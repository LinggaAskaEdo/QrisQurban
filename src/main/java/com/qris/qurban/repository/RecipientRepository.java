package com.qris.qurban.repository;

import com.qris.qurban.model.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long>
{
    Recipient findFirstByRecipientEmailIsAndRecipientPhoneIsAndRecipientYearIs(String recipientEmail, String recipientPhone, String recipientYear);
    Recipient findFirstByRecipientEmailIsAndRecipientPasswordIs(String recipientEmail, String recipientPassword);
}