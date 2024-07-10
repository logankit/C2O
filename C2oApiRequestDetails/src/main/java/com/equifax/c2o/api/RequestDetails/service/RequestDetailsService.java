package com.equifax.c2o.api.RequestDetails.service;

import com.equifax.c2o.api.RequestDetails.dto.RequestDetailsDTO;
import com.equifax.c2o.api.RequestDetails.dto.RequestDetailsResponseDTO;
import com.equifax.c2o.api.RequestDetails.exception.CustomException;
import com.equifax.c2o.api.RequestDetails.model.Request;
import com.equifax.c2o.api.RequestDetails.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestDetailsService {

    @Autowired
    private RequestRepository requestRepository;

    public RequestDetailsResponseDTO getRequestDetails(RequestDetailsDTO requestDetailsDTO) {
        // Validate input criteria
        validateRequestDetails(requestDetailsDTO);

        // Get current timestamp for toDate if not provided
        Timestamp toDate = requestDetailsDTO.getToDate() != null ? requestDetailsDTO.getToDate() : new Timestamp(System.currentTimeMillis());

        // Fetch data based on criteria
        List<Request> requests = fetchRequests(requestDetailsDTO, toDate);

        // Paginate results
        List<Request> paginatedRequests = requests.stream()
                .skip(requestDetailsDTO.getStartIndex())
                .limit(requestDetailsDTO.getPageLength())
                .collect(Collectors.toList());

        // Map to DTO
        List<RequestDetailsDTO> requestDetails = paginatedRequests.stream().map(this::mapToRequestDetailsDTO).collect(Collectors.toList());

        // Prepare response
        RequestDetailsResponseDTO responseDTO = new RequestDetailsResponseDTO();
        responseDTO.setTotalRecords(requests.size());
        responseDTO.setResults(requestDetails);

        return responseDTO;
    }

    private void validateRequestDetails(RequestDetailsDTO requestDetailsDTO) {
        if (requestDetailsDTO.getFromDate() != null && requestDetailsDTO.getFromDate().after(requestDetailsDTO.getToDate() != null ? requestDetailsDTO.getToDate() : new Timestamp(System.currentTimeMillis()))) {
            throw new CustomException("EFX_INVALID_SEARCH_FROMDATE", "From date cannot be in future");
        }

        if (requestDetailsDTO.getPageLength() < 1 || requestDetailsDTO.getPageLength() > 50) {
            throw new CustomException("EFX_INVALID_SEARCH_CRITERIA", "Invalid page length");
        }

        // Add other validations as required
    }

    private List<Request> fetchRequests(RequestDetailsDTO requestDetailsDTO, Timestamp toDate) {
        if (requestDetailsDTO.getBusinessUnit() != null && !requestDetailsDTO.getBusinessUnit().isEmpty()) {
            return requestRepository.findByBusinessUnitInAndCreatedDateBetween(requestDetailsDTO.getBusinessUnit(), requestDetailsDTO.getFromDate(), toDate);
        } else if (requestDetailsDTO.getContractId() != null) {
            return requestRepository.findByContractIdAndCreatedDateBetween(Long.parseLong(requestDetailsDTO.getContractId()), requestDetailsDTO.getFromDate(), toDate);
        } else if (requestDetailsDTO.getOrderId() != null) {
            return requestRepository.findByOrderIdAndCreatedDateBetween(Long.parseLong(requestDetailsDTO.getOrderId()), requestDetailsDTO.getFromDate(), toDate);
        } else {
            return requestRepository.findBySourceSystemIdAndCreatedDateBetween(requestDetailsDTO.getSourceSystem(), requestDetailsDTO.getFromDate(), toDate);
        }
    }

    private RequestDetailsDTO mapToRequestDetailsDTO(Request request) {
        RequestDetailsDTO requestDetailsDTO = new RequestDetailsDTO();
        requestDetailsDTO.setClientCorrelationId(request.getCorrelationId());
        requestDetailsDTO.setSourceSystem(request.getSourceSystemId());
        requestDetailsDTO.setBusinessUnit(List.of(request.getBusinessUnit()));
        requestDetailsDTO.setContractId(String.valueOf(request.getContractId()));
        requestDetailsDTO.setOrderId(String.valueOf(request.getOrderId()));
        requestDetailsDTO.setRequestStatus(String.valueOf(request.getRequestStatus()));
        return requestDetailsDTO;
    }
}
