package com.qris.qurban.controller;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.entity.Admin;
import com.qris.qurban.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController
{
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public Page<Admin> getAllAdmins(Pageable pageable)
    {
        return adminService.getAllAdmins(pageable);
    }

    @PostMapping("/admin")
    public Admin createAdmin(@Validated({Request.AddAdmin.class}) @RequestBody Request request)
    {
        Admin persistentAdmin = new Admin();
        persistentAdmin.setAdminName(request.getAdminName());
        persistentAdmin.setAdminEmail(request.getAdminEmail());
        persistentAdmin.setAdminPassword(request.getAdminPassword());

        return adminService.createAdmin(persistentAdmin);
    }

    @PostMapping("/admin-login")
    public Admin adminLogin(@Validated({Request.LoginAdmin.class}) @RequestBody Request request)
    {
        return adminService.adminLogin(request.getAdminEmail(), request.getAdminPassword());
    }
}