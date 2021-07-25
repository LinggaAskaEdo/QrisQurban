package com.qris.qurban.service;

import com.qris.qurban.model.Request;
import com.qris.qurban.model.entity.Package;
import com.qris.qurban.model.entity.Recipient;
import com.qris.qurban.model.exception.ConflictException;
import com.qris.qurban.model.exception.InternalServerErrorException;
import com.qris.qurban.model.exception.NotFoundException;
import com.qris.qurban.preference.ConfigPreference;
import com.qris.qurban.repository.AdminRepository;
import com.qris.qurban.repository.PackageRepository;
import com.qris.qurban.repository.RecipientRepository;
import com.qris.qurban.util.QrisQurbanUtil;
import com.qris.qurban.ws.QrisQurbanRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipientService
{
    private static final Logger logger = LogManager.getLogger();

    private final ConfigPreference preference;
    private final QrisQurbanUtil util;
    private final QrisQurbanRest rest;
    private final AdminRepository adminRepository;
    private final RecipientRepository recipientRepository;
    private final PackageRepository packageRepository;

    @Autowired
    public RecipientService(ConfigPreference preference, QrisQurbanUtil util, QrisQurbanRest rest, AdminRepository adminRepository, RecipientRepository recipientRepository, PackageRepository packageRepository)
    {
        this.preference = preference;
        this.util = util;
        this.rest = rest;
        this.adminRepository = adminRepository;
        this.recipientRepository = recipientRepository;
        this.packageRepository = packageRepository;
    }

    public Page<Recipient> getAllRecipients(String year, int page, int size, String[] sort)
    {
        try
        {
            List<Order> orders = new ArrayList<>();

            if (null != sort)
                getSortDirection(orders, sort);

            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
            Page<Recipient> recipientPage;

            if (null == year)
                recipientPage = recipientRepository.findAll(pageable);
            else
                recipientPage = recipientRepository.findByRecipientYearIs(year, pageable);

            if (null == recipientPage)
                throw new NotFoundException();

            return recipientPage;
        }
        catch (Exception e)
        {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private void getSortDirection(List<Order> orders, String[] sort)
    {
        logger.debug("sort: {}", Arrays.toString(sort));

        for (String sortOrder : sort)
        {
            if (sortOrder.contains(","))
            {
                String[] sortOrderSplit = sortOrder.split(",");
                orders.add(new Order(sortOrderSplit[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortOrderSplit[0]));
            }
            else
            {
                orders.add(new Order(Sort.Direction.ASC, sortOrder));
            }
        }
    }

    public Recipient addRecipient(Request request)
    {
        return adminRepository.findById(request.getAdminId()).map(admin -> {
            Recipient recipientExist = recipientRepository.findFirstByRecipientEmailIsAndRecipientPhoneIsAndRecipientYearIs(request.getRecipientEmail(), request.getRecipientPhone(), request.getRecipientYear());

            if (null != recipientExist)
                throw new ConflictException("Recipient with email: " + request.getRecipientEmail() +", phone: " + request.getRecipientPhone() + " and year: " + request.getRecipientYear() + " already exist !!!");

            String recipientPhone = request.getRecipientPhone();
            String recipientPhoto = preference.urlCloudStorage + util.saveCloudStorage(request.getRecipientPhoto());

            Recipient persistentRecipient = new Recipient();
            persistentRecipient.setAdmin(admin);
            persistentRecipient.setRecipientPhoto(recipientPhoto);
            persistentRecipient.setRecipientName(request.getRecipientName());

            String recipientPassword = util.generateRandomPassword();
            persistentRecipient.setRecipientPassword(recipientPassword);
            persistentRecipient.setRecipientEmail(request.getRecipientEmail());
            persistentRecipient.setRecipientPhone(recipientPhone);
            persistentRecipient.setRecipientGender(request.getRecipientGender());
            persistentRecipient.setRecipientYear(request.getRecipientYear());

            Recipient recipient = recipientRepository.save(persistentRecipient);

            String qrName = preference.urlCloudStorage + util.saveCloudStorage(util.generateQR(request.getRecipientName(), request.getRecipientEmail(), recipientPhone, request.getRecipientYear()));

            Package recipientPackage = new Package();
            recipientPackage.setPackageReceivedStatus(false);
            recipientPackage.setQrName(qrName);
            recipientPackage.setQrSentStatus(rest.sendMessage(recipientPhone, qrName, request.getRecipientEmail(), recipientPassword));
            recipientPackage.setRecipient(recipient);

            packageRepository.save(recipientPackage);

            return recipient;
        }).orElseThrow(NotFoundException::new);
    }

    public Recipient recipientLogin(String recipientEmail, String recipientPassword)
    {
        Recipient recipient = recipientRepository.findFirstByRecipientEmailIsAndRecipientPasswordIs(recipientEmail, recipientPassword);

        if (null == recipient)
            throw new NotFoundException();

        return recipient;
    }
}