package com.qris.qurban.service;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.entity.Package;
import com.qris.qurban.model.entity.Recipient;
import com.qris.qurban.model.exception.NotFoundException;
import com.qris.qurban.repository.PackageRepository;
import com.qris.qurban.repository.RecipientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService
{
    private static final Logger logger = LogManager.getLogger();

    private final RecipientRepository recipientRepository;
    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(RecipientRepository recipientRepository, PackageRepository packageRepository)
    {
        this.recipientRepository = recipientRepository;
        this.packageRepository = packageRepository;
    }

    public Package receivePackage(Request request)
    {
        Recipient recipientExist = recipientRepository.findFirstByRecipientEmailIsAndRecipientPhoneIsAndRecipientYearIs(request.getRecipientEmail(), request.getRecipientPhone(), request.getRecipientYear());

        if (null == recipientExist)
        {
            logger.error("Recipient not found !!!");

            throw new NotFoundException();
        }

        Package resultPackage = packageRepository.findFirstByRecipientRecipientId(recipientExist.getRecipientId());

        if (null == resultPackage)
        {
            logger.error("Package not found !!!");

            throw new NotFoundException();
        }

        resultPackage.setPackageReceivedStatus(true);

        return packageRepository.save(resultPackage);
    }
}