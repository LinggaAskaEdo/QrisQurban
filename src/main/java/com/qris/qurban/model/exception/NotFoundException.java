package com.qris.qurban.model.exception;

public class NotFoundException extends RuntimeException
{
    public NotFoundException()
    {
        // Do Nothing
    }

    public NotFoundException(String message)
    {
        super(message);
    }
}