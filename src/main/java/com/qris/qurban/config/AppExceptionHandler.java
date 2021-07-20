package com.qris.qurban.config;

import com.google.gson.Gson;
import com.qris.qurban.model.exception.BadRequestException;
import com.qris.qurban.model.exception.ConflictException;
import com.qris.qurban.model.exception.InternalServerErrorException;
import com.qris.qurban.model.exception.NotFoundException;
import com.qris.qurban.model.Response;
import com.qris.qurban.preference.ConstantPreference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(ConstraintViolationException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(errors));
    }

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<String> badRequest(BadRequestException ex)
    {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(ConstantPreference.RESPONSE_CODE_BAD_REQUEST, ConstantPreference.RESPONSE_MESSAGE_BAD_REQUEST, ex.toString()));
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<String> notFound(NotFoundException ex)
    {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(value = { ConflictException.class })
    protected ResponseEntity<String> conflict(ConflictException ex)
    {
        return ResponseEntity
                .status(409)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(ConstantPreference.RESPONSE_CODE_CONFLICT, ConstantPreference.RESPONSE_MESSAGE_CONFLICT, ex.toString()));
    }

    @ExceptionHandler(value = { InternalServerErrorException.class })
    protected ResponseEntity<String> serviceUnavailable(InternalServerErrorException ex)
    {
        return ResponseEntity
                .status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(ConstantPreference.RESPONSE_CODE_INTERNAL_SERVER_ERROR, ConstantPreference.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR, ex.toString()));
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<String> generalException(Exception ex)
    {
        return ResponseEntity
                .status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyResponse(ConstantPreference.RESPONSE_CODE_INTERNAL_SERVER_ERROR, ConstantPreference.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR, ex.toString()));
    }

    private String bodyResponse(int code, String message, String detailMessage)
    {
        return new Gson().toJson(new Response(code, message, detailMessage, "ERROR", true));
    }

    private String bodyResponse(Map<String, String> detailMessages)
    {
        return new Gson().toJson(new Response(ConstantPreference.RESPONSE_CODE_BAD_REQUEST, ConstantPreference.RESPONSE_MESSAGE_BAD_REQUEST, detailMessages, "ERROR", true));
    }
}