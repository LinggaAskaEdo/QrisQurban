package com.qris.qurban.model;

import com.google.gson.annotations.SerializedName;

public class Transaction
{
    private boolean success;
    private String description;

    @SerializedName("result_code")
    private int resultCode;

    public boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }

    @Override
    public String toString()
    {
        return "ResponseSend{" +
                "success='" + success + '\'' +
                ", description='" + description + '\'' +
                ", result_code='" + resultCode + '\'' +
                '}';
    }
}