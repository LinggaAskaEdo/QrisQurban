package com.qris.qurban.ws;

import com.qris.qurban.model.Transaction;
import com.qris.qurban.model.exception.InternalServerErrorException;
import com.qris.qurban.preference.ConfigPreference;
import com.qris.qurban.util.QrisQurbanUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Component
public class QrisQurbanRest
{
    private static final Logger logger = LogManager.getLogger();

    private static final String ENCODE = "UTF-8";

    private final ConfigPreference preference;
    private final QrisQurbanUtil util;
    private final RestTemplate restTemplate;

    @Autowired
    public QrisQurbanRest(ConfigPreference preference, QrisQurbanUtil util, RestTemplate restTemplate)
    {
        this.preference = preference;
        this.util = util;
        this.restTemplate = restTemplate;
    }

    public boolean sendMessage(String recipientPhone, String recipientPhoto, String recipientEmail, String recipientPassword)
    {
        boolean result = false;

        try
        {
            String sendMessageUrl = preference.apiwhaUrlSendMessage + URLEncoder.encode(preference.apiwhaApiKey, ENCODE)
                    + "&number=" + URLEncoder.encode(recipientPhone, ENCODE)
                    + "&text=";

            if (util.urlValidator(sendMessageUrl))
            {
                logger.debug("{} is valid URL", sendMessageUrl);

                String text =  preference.masjidText + " " + recipientPhoto + "\n\nUsername: " + recipientEmail + "\nPassword: " + recipientPassword;

                logger.debug("text: {}", text);

                Transaction transaction = restTemplate.getForObject(sendMessageUrl + text, Transaction.class);

                if (null != transaction)
                {
                    logger.debug("Sending message to {} is {}, description={}, code={}", recipientPhone, transaction.getSuccess() ? "success" : "failed", transaction.getDescription(), transaction.getResultCode());
                    result = true;
                }
                else
                {
                    logger.debug("Sending message to {} is failed", recipientPhone);
                }
            }
            else
            {
                logger.debug("{} is not valid URL", sendMessageUrl);
            }
        }
        catch (Exception e)
        {
            logger.error("Error when sendMessage: ", e);

            throw new InternalServerErrorException(e.getMessage());
        }

        return result;
    }
}