package com.qris.qurban.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qris.qurban.model.entity.Recipient;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response
{
    // Error
    private Integer code;
    private String message;
    private String detailMessage;
    private Map<String, String> detailMessages;
    private String severity;
    private Boolean error;

    private Recipient recipient;
    private int totalRecipients;
    private int totalSent;
    private int totalRedeemed;

    public Response()
    {}

    public Response(int code, String message, String detailMessage, String severity, boolean error)
    {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.severity = severity;
        this.error = error;
    }

    public Response(Integer code, String message, Map<String, String> detailMessages, String severity, Boolean error)
    {
        this.code = code;
        this.message = message;
        this.detailMessages = detailMessages;
        this.severity = severity;
        this.error = error;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
    }

    public Map<String, String> getDetailMessages()
    {
        return detailMessages;
    }

    public void setDetailMessages(Map<String, String> detailMessages)
    {
        this.detailMessages = detailMessages;
    }

    public String getSeverity()
    {
        return severity;
    }

    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    public Boolean getError()
    {
        return error;
    }

    public void setError(Boolean error)
    {
        this.error = error;
    }

    public Recipient getRecipient()
    {
        return recipient;
    }

    public void setRecipient(Recipient recipient)
    {
        this.recipient = recipient;
    }

    public int getTotalRecipients()
    {
        return totalRecipients;
    }

    public void setTotalRecipients(int totalRecipients)
    {
        this.totalRecipients = totalRecipients;
    }

    public int getTotalSent()
    {
        return totalSent;
    }

    public void setTotalSent(int totalSent)
    {
        this.totalSent = totalSent;
    }

    public int getTotalRedeemed()
    {
        return totalRedeemed;
    }

    public void setTotalRedeemed(int totalRedeemed)
    {
        this.totalRedeemed = totalRedeemed;
    }
}