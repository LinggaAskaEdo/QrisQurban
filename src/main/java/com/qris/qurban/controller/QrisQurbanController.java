package com.qris.qurban.controller;

import com.google.cloud.storage.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class QrisQurbanController
{
    private static final Logger logger = LogManager.getLogger();

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

    @PutMapping("/upload")
    public ResponseEntity<String> upload()
    {
        // The ID of your GCP project
        String projectId = "QrisQurban";

        // The ID of your GCS bucket
        String bucketName = "storage-qris-qurban";

        // The ID of your GCS object
        String objectName = "testUpload3";

        // The path to your file to upload
        String filePath = "/Users/lingga.putra/Documents/Picture/unnamed.jpg";

         try
         {
             Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
             BlobId blobId = BlobId.of(bucketName, objectName);
             BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
             storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
             storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

             logger.info("File {} uploaded to bucker {} as {}", filePath, bucketName, objectName);

             return ResponseEntity.ok("Success !!!");
         }
         catch (Exception e)
         {
             logger.error("Error when upload: ", e);
             return ResponseEntity.internalServerError().build();
         }
    }
}