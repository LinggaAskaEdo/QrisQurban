package com.qris.qurban.ws;

import com.qris.qurban.model.Transaction;
import com.qris.qurban.model.exception.InternalServerErrorException;
import com.qris.qurban.preference.ConfigPreference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class QrisQurbanRest
{
    private static final Logger logger = LogManager.getLogger();

    private static final String ENCODE = "UTF-8";
    private final ConfigPreference preference;
    private final RestTemplate restTemplate;

    @Autowired
    public QrisQurbanRest(ConfigPreference preference, RestTemplate restTemplate)
    {
        this.preference = preference;
        this.restTemplate = restTemplate;
    }

    public boolean sendMessage(String recipientPhone, String recipientPhoto, String recipientEmail, String recipientPassword)
    {
        boolean result = false;

        try
        {
            String text =  preference.masjidText + " " + recipientPhoto + "\n\nUsername: " + recipientEmail + "\nPassword: " + recipientPassword;

            logger.debug("message: {}", text);

            String sendMessageUrl = preference.apiwhaUrlSendMessage + URLEncoder.encode(preference.apiwhaApiKey, ENCODE)
                    + "&number=" + URLEncoder.encode(recipientPhone, ENCODE)
                    + "&text=" + text;

            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);

            Transaction transaction = restTemplate.getForObject(sendMessageUrl, Transaction.class);

            if (null != transaction)
            {
                logger.debug("Sending message to {} is {}, description={}, code={}", recipientPhone, transaction.getSuccess() ? "success" : "failed", transaction.getDescription(), transaction.getResultCode());
                result = true;
            }
            else
                logger.debug("Sending message to {} is failed", recipientPhone);
        }
        catch (Exception e)
        {
            logger.error("Error when sendMessage: ", e);

            throw new InternalServerErrorException(e.getMessage());
        }

        return result;
    }
}