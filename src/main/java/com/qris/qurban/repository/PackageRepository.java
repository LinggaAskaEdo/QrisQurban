package com.qris.qurban.repository;

import com.qris.qurban.model.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Long>
{
    Package findFirstByRecipientRecipientId(Long recipientId);
}