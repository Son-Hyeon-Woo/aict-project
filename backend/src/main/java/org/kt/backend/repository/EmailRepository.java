package org.kt.backend.repository;

import java.util.Optional;
import java.util.List;
import org.kt.backend.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
        Optional<Email> findFirstByProcessStatusOrderByReceivedDateAsc(String processStatus);

        List<Email> findByProcessStatusOrderByReceivedDateAsc(String processStatus, Pageable pageable);

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

        // 👉 - 주어진 기간 동안의 이메일 개수를 반환하는 쿼리
        @Query("SELECT COUNT(e) FROM Email e WHERE e.receivedDate BETWEEN :start AND :end")
        long countEmailsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

        // 👉 - 금일 받은 이메일의 유형별 개수를 반환하는 쿼리
        @Query("SELECT r.riskDetail, COUNT(e) FROM Email e JOIN e.riskLevel r WHERE r.riskDetail != '유형없음' AND e.receivedDate >= :startOfDay AND e.receivedDate < :endOfDay GROUP BY r.riskDetail")
        List<Object[]> countEmailsByRiskDetail(@Param("startOfDay") LocalDateTime startOfDay,
                        @Param("endOfDay") LocalDateTime endOfDay);

        // 👉 - 차단된 이메일의 정보를 조회하는 쿼리
        @Query("SELECT e FROM Email e JOIN e.riskLevel r WHERE e.processResult = '차단' ORDER BY r.detectionDate DESC")
        List<Email> findTop10BlockedEmailsOrderByDetectionDate(Pageable pageable);

}
