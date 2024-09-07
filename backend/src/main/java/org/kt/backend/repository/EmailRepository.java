package org.kt.backend.repository;

import java.util.Optional;
import java.util.List;
import org.kt.backend.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    Optional<Email> findFirstByProcessStatusOrderByReceivedDateAsc(String processStatus);

    List<Email> findByProcessStatusNot(String processStatus);

    @Query("SELECT COUNT(e) FROM Email e WHERE e.receivedDate >= :startOfDay AND e.receivedDate < :endOfDay")
    long countTodayReceivedEmails(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(e) FROM Email e JOIN e.riskLevel r WHERE e.receivedDate >= :startOfDay AND e.receivedDate < :endOfDay AND r.riskLevel != '안전'")
    long countTodayHighRiskEmails(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(e) FROM Email e JOIN e.riskLevel r WHERE e.receivedDate >= :startOfDay AND e.receivedDate < :endOfDay AND r.riskLevel != '안전' AND e.processResult = '차단'")
    long countTodayBlockedHighRiskEmails(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
