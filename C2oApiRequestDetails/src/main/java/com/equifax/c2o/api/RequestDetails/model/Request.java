package com.equifax.c2o.api.RequestDetails.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "c2o_api_request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "source_system_id")
    private String sourceSystemId;

    @Column(name = "corelation_id")
    private String correlationId;

    @Column(name = "request_status")
    private int requestStatus;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_updated_date")
    private Timestamp lastUpdatedDate;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "case_id")
    private String caseId;

    @Column(name = "request_payload2", columnDefinition = "CLOB")
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "CLOB")
    private String responsePayload;

    // Getters and Setters
}
