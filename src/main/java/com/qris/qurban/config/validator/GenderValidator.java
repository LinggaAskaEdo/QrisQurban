package com.qris.qurban.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String>
{
    public void initialize(Gender constraint)
    {
        //Do nothing
    }

    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return value.equalsIgnoreCase("M") || value.equalsIgnoreCase("F");
    }
}