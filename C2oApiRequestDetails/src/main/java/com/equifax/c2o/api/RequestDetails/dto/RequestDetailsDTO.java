package com.equifax.c2o.api.RequestDetails.dto;

import java.sql.Timestamp;
import java.util.List;

public class RequestDetailsDTO {
    private String clientCorrelationId;
    private String sourceSystem;
    private Timestamp fromDate;
    private Timestamp toDate;
    private List<String> businessUnit;
    private String contractId;
    private String orderId;
    private String requestStatus;
    private int startIndex = 0;
    private int pageLength = 20;

    // Getters and Setters
}
