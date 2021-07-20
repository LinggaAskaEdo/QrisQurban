package com.qris.qurban.model;

import java.util.Map;

public class Response
{
    // Error
    private Integer code;
    private String message;
    private String detailMessage;
    private Map<String, String> detailMessages;
    private String serverity;
    private Boolean error;

    public Response(int code, String message, String detailMessage, String serverity, boolean error)
    {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.serverity = serverity;
        this.error = error;
    }

    public Response(Integer code, String message, Map<String, String> detailMessages, String serverity, Boolean error)
    {
        this.code = code;
        this.message = message;
        this.detailMessages = detailMessages;
        this.serverity = serverity;
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

    public String getServerity()
    {
        return serverity;
    }

    public void setServerity(String serverity)
    {
        this.serverity = serverity;
    }

    public Boolean getError()
    {
        return error;
    }

    public void setError(Boolean error)
    {
        this.error = error;
    }
}