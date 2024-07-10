package com.equifax.c2o.api.RequestDetails.repository;

import com.equifax.c2o.api.RequestDetails.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findBySourceSystemIdAndCreatedDateBetween(String sourceSystemId, Timestamp fromDate, Timestamp toDate);
    List<Request> findByBusinessUnitInAndCreatedDateBetween(List<String> businessUnits, Timestamp fromDate, Timestamp toDate);
    List<Request> findByContractIdAndCreatedDateBetween(Long contractId, Timestamp fromDate, Timestamp toDate);
    List<Request> findByOrderIdAndCreatedDateBetween(Long orderId, Timestamp fromDate, Timestamp toDate);
    Request findByCorrelationId(String correlationId);
}
