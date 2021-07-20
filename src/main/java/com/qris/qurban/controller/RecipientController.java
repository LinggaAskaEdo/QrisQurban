package com.qris.qurban.controller;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.entity.Admin;
import com.qris.qurban.model.entity.Recipient;
import com.qris.qurban.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipientController
{
    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipientService)
    {
        this.recipientService = recipientService;
    }

    @PostMapping("/recipient")
    public Recipient addRecipient(@Validated({Request.AddRecipient.class}) @RequestBody Request request)
    {
        return recipientService.addRecipient(request);
    }

    @GetMapping("/recipient-login")
    public Recipient recipientLogin(@Validated({Request.RecipientLogin.class}) @RequestBody Request request)
    {
        return recipientService.recipientLogin(request.getRecipientEmail(), request.getRecipientPassword());
    }
}