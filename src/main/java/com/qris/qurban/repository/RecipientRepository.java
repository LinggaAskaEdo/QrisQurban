package com.qris.qurban.repository;

import com.qris.qurban.model.entity.Recipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long>
{
    Page<Recipient> findByRecipientYearIs(String year, Pageable pageable);
    List<Recipient> findByRecipientYearIs(String year);
    Recipient findFirstByRecipientEmailIsAndRecipientPhoneIsAndRecipientYearIs(String recipientEmail, String recipientPhone, String recipientYear);
    Recipient findFirstByRecipientEmailIsAndRecipientPasswordIs(String recipientEmail, String recipientPassword);
}