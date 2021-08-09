package com.qris.qurban.controller;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.Response;
import com.qris.qurban.model.entity.Recipient;
import com.qris.qurban.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipientController
{
    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipientService)
    {
        this.recipientService = recipientService;
    }

    @GetMapping("/recipients")
    public Page<Recipient> getAllRecipients(@RequestParam(required = false) String year, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "recipientId,asc") String[] sort)
    {
        return recipientService.getAllRecipients(year, page, size, sort);
    }

    @PostMapping("/recipient")
    public Recipient addRecipient(@Validated({Request.AddRecipient.class}) @RequestBody Request request)
    {
        return recipientService.addRecipient(request);
    }

    @PostMapping("/recipient-login")
    public Response recipientLogin(@Validated({Request.RecipientLogin.class}) @RequestBody Request request)
    {
        return recipientService.recipientLogin(request.getRecipientEmail(), request.getRecipientPassword());
    }
}