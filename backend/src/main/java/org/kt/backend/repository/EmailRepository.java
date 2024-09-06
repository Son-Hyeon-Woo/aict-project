package org.kt.backend.repository;

import java.util.Optional;
import java.util.List;
import org.kt.backend.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
  Optional<Email> findFirstByProcessStatusOrderByReceivedDateAsc(String processStatus);
  List<Email> findByProcessStatusNot(String processStatus);
}
