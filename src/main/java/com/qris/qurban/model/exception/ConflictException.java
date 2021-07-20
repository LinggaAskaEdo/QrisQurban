package com.qris.qurban.model.exception;

public class ConflictException extends RuntimeException
{
    public ConflictException()
    {
        // Do Nothing
    }

    public ConflictException(String message)
    {
        super(message);
    }
}