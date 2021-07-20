package com.qris.qurban.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADMIN")
public class Admin extends AuditModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID", nullable = false, updatable = false)
    private Long adminId;

    @NotNull
    @Size(max = 50)
    @Column(name = "ADMIN_NAME", nullable = false)
    private String adminName;

    @NotNull
    @Size(max = 50)
    @Column(name = "ADMIN_EMAIL", nullable = false)
    private String adminEmail;

    @NotNull
    @Size(max = 50)
    @Column(name = "ADMIN_PASSWORD", nullable = false)
    private String adminPassword;

    public Long getAdminId()
    {
        return adminId;
    }

    public void setAdminId(Long adminId)
    {
        this.adminId = adminId;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public String getAdminEmail()
    {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail)
    {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword()
    {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword)
    {
        this.adminPassword = adminPassword;
    }
}