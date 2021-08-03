package com.qris.qurban.service;

import com.qris.qurban.model.entity.Admin;
import com.qris.qurban.model.exception.NotFoundException;
import com.qris.qurban.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository)
    {
        this.adminRepository = adminRepository;
    }

    public Page<Admin> getAllAdmins(Pageable pageable)
    {
        return adminRepository.findAll(pageable);
    }

    public Admin createAdmin(Admin persistentAdmin)
    {
        return adminRepository.save(persistentAdmin);
    }

    public Admin adminLogin(String adminEmail, String adminPassword)
    {
        Admin admin = adminRepository.findFirstByAdminEmailIsAndAdminPasswordIs(adminEmail, adminPassword);

        if (null == admin)
            throw new NotFoundException();

        return admin;
    }

    public ResponseEntity<Object> deleteAdmin(Long adminId)
    {
        return adminRepository.findById(adminId).map(admin -> {
            adminRepository.delete(admin);

            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NotFoundException("AdminId " + adminId + " not found"));
    }
}