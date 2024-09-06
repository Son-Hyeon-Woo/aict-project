package org.kt.backend.repository;

import org.kt.backend.entity.EmailContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailContentRepository extends JpaRepository<EmailContent, Integer> {

}
