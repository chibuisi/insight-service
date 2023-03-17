package com.chibuisi.dailyinsightservice.mail.repository;

import com.chibuisi.dailyinsightservice.mail.model.SentMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentMailRepository extends JpaRepository<SentMail, Long> {

}
