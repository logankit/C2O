package com.equifax.c2o.api.dto;

import java.util.List;

public class RequestDetailsResponse {
    private int totalRecords;
    private List<Result> results;

    public static class Result {
        private Long requestId;
        private String sourceSystem;
        private String businessUnit;
        private String correlationId;
        private Integer contractId;
        private String orderId;
        private String requestType;
        private String requestDate;
        private String requestStatus;
        private String businessCaseId;

        // Getters and Setters
        public Long getRequestId() {
            return requestId;
        }

        public void setRequestId(Long requestId) {
            this.requestId = requestId;
        }

        public String getSourceSystem() {
            return sourceSystem;
        }

        public void setSourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
        }

        public String getBusinessUnit() {
            return businessUnit;
        }

        public void setBusinessUnit(String businessUnit) {
            this.businessUnit = businessUnit;
        }

        public String getCorrelationId() {
            return correlationId;
        }

        public void setCorrelationId(String correlationId) {
            this.correlationId = correlationId;
        }

        public Integer getContractId() {
            return contractId;
        }

        public void setContractId(Integer contractId) {
            this.contractId = contractId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(String requestStatus) {
            this.requestStatus = requestStatus;
        }

        public String getBusinessCaseId() {
            return businessCaseId;
        }

        public void setBusinessCaseId(String businessCaseId) {
            this.businessCaseId = businessCaseId;
        }
    }

    // Getters and Setters
    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
