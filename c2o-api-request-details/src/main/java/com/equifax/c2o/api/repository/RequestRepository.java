package com.equifax.c2o.api.repository;

import com.equifax.c2o.api.model.RequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestDetails, Long> {
    Optional<RequestDetails> findByCorrelationId(String correlationId);
}
