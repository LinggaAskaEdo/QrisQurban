package com.qris.qurban.controller;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.entity.Package;
import com.qris.qurban.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageController
{
    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService)
    {
        this.packageService = packageService;
    }

    @PutMapping("/receive-package")
    public Package receivePackage(@Validated({Request.ReceivePackage.class}) @RequestBody Request request)
    {
        return packageService.receivePackage(request);
    }
}