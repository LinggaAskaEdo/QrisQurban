package com.qris.qurban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrisQurbanController
{
    private final BuildProperties buildProperties;

    @Autowired
    public QrisQurbanController(BuildProperties buildProperties)
    {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/")
    public ResponseEntity<String> getHome()
    {
        return ResponseEntity.ok("Hello World !!!");
    }

    @GetMapping("/version")
    public ResponseEntity<String> getVersion()
    {
        return ResponseEntity.ok(buildProperties.getVersion());
    }
}