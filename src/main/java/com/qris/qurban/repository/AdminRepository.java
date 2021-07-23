package com.qris.qurban.repository;

import com.qris.qurban.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>
{
    Admin findFirstByAdminEmailIsAndAdminPasswordIs(String adminName, String adminPassword);
}