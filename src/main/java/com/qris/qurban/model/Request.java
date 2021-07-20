package com.qris.qurban.model;

import com.qris.qurban.config.validator.Gender;

import javax.validation.constraints.*;

public class Request
{
    @NotBlank(groups = AddAdmin.class)
    private String adminName;

    @NotBlank(groups = {AddAdmin.class, LoginAdmin.class})
    @Email(groups = {AddAdmin.class, LoginAdmin.class})
    private String adminEmail;

    @NotBlank(groups = {AddAdmin.class, LoginAdmin.class})
    private String adminPassword;

    @NotNull(groups = AddRecipient.class)
    private Long adminId;

    @NotBlank(groups = AddRecipient.class)
    private String recipientPhoto;

    @NotBlank(groups = AddRecipient.class)
    private String recipientName;

    @NotBlank(groups = {AddRecipient.class, RecipientLogin.class})
    @Email(groups = {AddRecipient.class, RecipientLogin.class})
    private String recipientEmail;

    @NotBlank(groups = AddRecipient.class)
    @Size(groups = AddRecipient.class, max = 15)
    @Pattern(groups = AddRecipient.class, regexp = "^(\\+62|62)?[\\s-]?0?8[1-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,5}$", message = "Value must be in Indonesia mobile number format eg. 6281911111111")
    private String recipientPhone;

    @NotBlank(groups = AddRecipient.class)
    @Size(groups = AddRecipient.class, max = 1)
    @Gender(groups = AddRecipient.class)
    private String recipientGender;

    @NotBlank(groups = AddRecipient.class)
    @Size(groups = AddRecipient.class, min = 4, max = 4, message = "size must be 4")
    @Pattern(groups = AddRecipient.class, regexp = "[0-9]+", message = "Value must be numeric")
    private String recipientYear;

    @NotBlank(groups = RecipientLogin.class)
    private String recipientPassword;

    public interface AddAdmin
    {}

    public interface LoginAdmin
    {}

    public interface AddRecipient
    {}

    public interface RecipientLogin
    {}

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

    public Long getAdminId()
    {
        return adminId;
    }

    public void setAdminId(Long adminId)
    {
        this.adminId = adminId;
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

    public String getRecipientPassword()
    {
        return recipientPassword;
    }

    public void setRecipientPassword(String recipientPassword)
    {
        this.recipientPassword = recipientPassword;
    }
}