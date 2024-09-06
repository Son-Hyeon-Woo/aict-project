package org.kt.backend.repository;

import org.kt.backend.entity.EmailRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRecipientRepository extends JpaRepository<EmailRecipient, Integer> {

}
