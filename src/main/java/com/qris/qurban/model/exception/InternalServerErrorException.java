package com.qris.qurban.model.exception;

public class InternalServerErrorException extends RuntimeException
{
    public InternalServerErrorException()
    {
        // Do Nothing
    }

    public InternalServerErrorException(String message)
    {
        super(message);
    }
}