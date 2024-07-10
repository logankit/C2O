package com.equifax.c2o.api.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "c2o_api_request")
public class RequestDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String sourceSystemId;
    private String correlationId;
    private Integer requestStatus;
    private Integer contractId;
    private String contractType;
    private Integer leEfxId;
    private String billingSource;
    private String businessUnit;
    private Timestamp createdDate;
    private Timestamp lastUpdatedDate;
    private String responsePayload;
    private String headerPayload;
    private String responseSent;
    private String requestPayload2;
    private String requestType;
    private Integer baseContractId;
    private Timestamp responseSentOn;
    private String redriveFlag;
    private String caseId;

    // Getters and Setters
}
