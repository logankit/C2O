package com.equifax.c2o.api.controller;

import com.equifax.c2o.api.dto.RequestDetailsResponse;
import com.equifax.c2o.api.exception.ApiException;
import com.equifax.c2o.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/status-api")
public class StatusApiController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public ResponseEntity<?> getRequestDetails(
            @RequestHeader("ClientcorrelationId") String clientCorrelationId,
            @RequestHeader("SourceSystem") String sourceSystem,
            @RequestParam(value = "fromDate", required = false) Timestamp fromDate,
            @RequestParam(value = "toDate", required = false) Timestamp toDate,
            @RequestParam(value = "businessUnit", required = false) List<String> businessUnit,
            @RequestParam(value = "contractId", required = false) String contractId,
            @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam(value = "requestStatus", required = false) String requestStatus,
            @RequestParam(value = "startIndex", defaultValue = "0") int startIndex,
            @RequestParam(value = "pageLength", defaultValue = "20") int pageLength) {

        if (pageLength < 1 || pageLength > 50) {
            throw new ApiException("EFX_INVALID_SEARCH_CRITERIA", "Invalid page length. Must be between 1 and 50.");
        }

        if (fromDate != null && toDate != null && fromDate.after(toDate)) {
            throw new ApiException("EFX_INVALID_SEARCH_FROMDATE", "From date cannot be in future.");
        }

        RequestDetailsResponse response = requestService.getRequestDetails(
                clientCorrelationId, sourceSystem, fromDate, toDate, businessUnit, contractId, orderId, requestStatus, startIndex, pageLength);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payload")
    public ResponseEntity<?> getRequestPayload(
            @RequestHeader("correlationId") String correlationId,
            @RequestParam(value = "reqType", defaultValue = "RequestPayload") String reqType) {

        String payload = requestService.getRequestPayload(correlationId, reqType);
        return ResponseEntity.ok(payload);
    }
}
