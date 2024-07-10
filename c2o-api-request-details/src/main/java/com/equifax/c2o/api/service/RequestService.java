package com.equifax.c2o.api.service;

import com.equifax.c2o.api.dto.RequestDetailsResponse;
import com.equifax.c2o.api.exception.ApiException;
import com.equifax.c2o.api.model.RequestDetails;
import com.equifax.c2o.api.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public RequestDetailsResponse getRequestDetails(String clientCorrelationId, String sourceSystem, Timestamp fromDate, Timestamp toDate, 
                                                    List<String> businessUnit, String contractId, String orderId, String requestStatus, 
                                                    int startIndex, int pageLength) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RequestDetails> query = cb.createQuery(RequestDetails.class);
        Root<RequestDetails> root = query.from(RequestDetails.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("correlationId"), clientCorrelationId));
        predicates.add(cb.equal(root.get("sourceSystemId"), sourceSystem));

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), toDate));
        }
        if (businessUnit != null && !businessUnit.isEmpty()) {
            predicates.add(root.get("businessUnit").in(businessUnit));
        }
        if (contractId != null && !contractId.isEmpty()) {
            predicates.add(cb.equal(root.get("contractId"), contractId));
        }
        if (orderId != null && !orderId.isEmpty()) {
            predicates.add(cb.equal(root.get("orderId"), orderId));
        }
        if (requestStatus != null && !requestStatus.isEmpty()) {
            predicates.add(cb.equal(root.get("requestStatus"), requestStatus));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));
        
        List<RequestDetails> results = entityManager.createQuery(query)
                .setFirstResult(startIndex)
                .setMaxResults(pageLength)
                .getResultList();

        RequestDetailsResponse response = new RequestDetailsResponse();
        response.setTotalRecords(results.size());
        response.setResults(mapToResponse(results));
        return response;
    }

    public String getRequestPayload(String correlationId, String reqType) {
        RequestDetails requestDetails = requestRepository.findByCorrelationId(correlationId)
                .orElseThrow(() -> new ApiException("EFX_COR_ID_NOT_FOUND", "No request payload found with mentioned correlation id: " + correlationId));
        return "RequestPayload".equalsIgnoreCase(reqType) ? requestDetails.getRequestPayload2() : requestDetails.getResponsePayload();
    }

    private List<RequestDetailsResponse.Result> mapToResponse(List<RequestDetails> requestDetailsList) {
        List<RequestDetailsResponse.Result> results = new ArrayList<>();
        for (RequestDetails requestDetails : requestDetailsList) {
            RequestDetailsResponse.Result result = new RequestDetailsResponse.Result();
            result.setRequestId(requestDetails.getRequestId());
            result.setSourceSystem(requestDetails.getSourceSystemId());
            result.setBusinessUnit(requestDetails.getBusinessUnit());
            result.setCorrelationId(requestDetails.getCorrelationId());
            result.setContractId(String.valueOf(requestDetails.getContractId()));
            result.setOrderId(requestDetails.getOrderId());
            result.setRequestType(requestDetails.getRequestType());
            result.setRequestDate(requestDetails.getCreatedDate().toString());
            result.setRequestStatus(String.valueOf(requestDetails.getRequestStatus()));
            result.setBusinessCaseId(requestDetails.getCaseId());
            results.add(result);
        }
        return results;
    }
}
