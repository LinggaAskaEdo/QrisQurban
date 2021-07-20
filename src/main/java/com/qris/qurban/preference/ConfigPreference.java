package com.qris.qurban.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigPreference
{
    @Value("${url.cloud.storage}")
    public String urlCloudStorage;

    @Value("${project.id}")
    public String projectId;

    @Value("${bucket.name}")
    public String bucketName;

    @Value("${rest.timeout.connection}")
    public int restTimeout;

    @Value("${http.pooling.client.validate.after.inactivity}")
    public int validateAfterInactivity;

    @Value("${http.pooling.client.max.per.route}")
    public int maxPerRoute;

    @Value("${http.pooling.client.max.total}")
    public int maxTotal;

    @Value("${apiwha.api.key}")
    public String apiwhaApiKey;

    @Value("${apiwha.url.send.message}")
    public String apiwhaUrlSendMessage;

    @Value("${masjid.logo.url}")
    public String masjidLogoUrl;

    @Value("${masjid.text}")
    public String masjidText;
}