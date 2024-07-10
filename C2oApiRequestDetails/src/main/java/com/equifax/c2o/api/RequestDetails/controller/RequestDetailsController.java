package com.equifax.c2o.api.RequestDetails.controller;

import com.equifax.c2o.api.RequestDetails.dto.RequestDetailsDTO;
import com.equifax.c2o.api.RequestDetails.dto.RequestDetailsResponseDTO;
import com.equifax.c2o.api.RequestDetails.dto.StatusUpdateRequest;
import com.equifax.c2o.api.RequestDetails.service.RequestDetailsService;
import com.equifax.c2o.api.RequestDetails.service.StatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;

@RestController
@RequestMapping("/status-api")
public class RequestDetailsController {

    @Autowired
    private RequestDetailsService requestDetailsService;

    @Autowired
    private StatusUpdateService statusUpdateService;

    @GetMapping
    public RequestDetailsResponseDTO getRequestDetails(
            @RequestHeader("ClientCorrelationId") String clientCorrelationId,
            @RequestHeader("SourceSystem") String sourceSystem,
            @RequestParam(required = false) Long fromDate,
            @RequestParam(required = false) Long toDate,
            @RequestParam(required = false) String[] businessUnit,
            @RequestParam(required = false) String contractId,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String requestStatus,
            @RequestParam(defaultValue = "0") int startIndex,
            @RequestParam(defaultValue = "20") int pageLength) {

        RequestDetailsDTO requestDetailsDTO = new RequestDetailsDTO();
        requestDetailsDTO.setClientCorrelationId(clientCorrelationId);
        requestDetailsDTO.setSourceSystem(sourceSystem);
        requestDetailsDTO.setFromDate(fromDate != null ? new Timestamp(fromDate) : null);
        requestDetailsDTO.setToDate(toDate != null ? new Timestamp(toDate) : null);
        requestDetailsDTO.setBusinessUnit(businessUnit != null ? Arrays.asList(businessUnit) : null);
        requestDetailsDTO.setContractId(contractId);
        requestDetailsDTO.setOrderId(orderId);
        requestDetailsDTO.setRequestStatus(requestStatus);
        requestDetailsDTO.setStartIndex(startIndex);
        requestDetailsDTO.setPageLength(pageLength);

        return requestDetailsService.getRequestDetails(requestDetailsDTO);
    }

    @PutMapping
    public ResponseEntity<String> updateStatus(
            @RequestHeader("ClientCorrelationId") String clientCorrelationId,
            @RequestHeader("SourceSystem") String sourceSystem,
            @RequestBody StatusUpdateRequest statusUpdateRequest) {

        statusUpdateService.updateStatus(statusUpdateRequest);
        return ResponseEntity.ok("CAPI Status update received successfully");
    }
}
