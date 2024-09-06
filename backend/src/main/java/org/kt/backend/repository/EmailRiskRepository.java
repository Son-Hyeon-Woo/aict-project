package org.kt.backend.repository;

import org.kt.backend.entity.EmailRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRiskRepository extends JpaRepository<EmailRisk, Integer> {

}
