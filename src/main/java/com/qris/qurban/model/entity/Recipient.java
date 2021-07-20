package com.qris.qurban.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RECIPIENT")
public class Recipient extends AuditModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPIENT_ID", nullable = false, updatable = false)
    private Long recipientId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Admin admin;

    @NotNull
    @Size(max = 100)
    @Column(name = "recipient_photo", nullable = false)
    private String recipientPhoto;

    @NotNull
    @Size(max = 50)
    @Column(name = "RECIPIENT_NAME", nullable = false)
    private String recipientName;

    @NotNull
    @Size(max = 50)
    @Column(name = "RECIPIENT_PASSWORD", nullable = false)
    private String recipientPassword;

    @NotNull
    @Size(max = 50)
    @Column(name = "RECIPIENT_EMAIL", nullable = false)
    private String recipientEmail;

    @NotNull
    @Size(max = 20)
    @Column(name = "RECIPIENT_PHONE", nullable = false)
    private String recipientPhone;

    @NotNull
    @Size(max = 1)
    @Column(name = "RECIPIENT_GENDER", nullable = false)
    private String recipientGender;

    @NotNull
    @Size(max = 4)
    @Column(name = "RECIPIENT_YEAR", nullable = false)
    private String recipientYear;

    public Long getRecipientId()
    {
        return recipientId;
    }

    public void setRecipientId(Long recipientId)
    {
        this.recipientId = recipientId;
    }

    public Admin getAdmin()
    {
        return admin;
    }

    public void setAdmin(Admin admin)
    {
        this.admin = admin;
    }

    public String getRecipientPhoto()
    {
        return recipientPhoto;
    }

    public void setRecipientPhoto(String recipientPhoto)
    {
        this.recipientPhoto = recipientPhoto;
    }

    public String getRecipientName()
    {
        return recipientName;
    }

    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }

    public String getRecipientPassword()
    {
        return recipientPassword;
    }

    public void setRecipientPassword(String recipientPassword)
    {
        this.recipientPassword = recipientPassword;
    }

    public String getRecipientEmail()
    {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail)
    {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientPhone()
    {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientGender()
    {
        return recipientGender;
    }

    public void setRecipientGender(String recipientGender)
    {
        this.recipientGender = recipientGender;
    }

    public String getRecipientYear()
    {
        return recipientYear;
    }

    public void setRecipientYear(String recipientYear)
    {
        this.recipientYear = recipientYear;
    }
}