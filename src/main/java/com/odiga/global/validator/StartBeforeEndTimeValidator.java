package com.odiga.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalTime;

public class StartBeforeEndTimeValidator implements ConstraintValidator<StartBeforeEndTime, Object> {

    private String message;
    private String startTime;
    private String endTime;

    @Override
    public void initialize(StartBeforeEndTime constraintAnnotation) {
        message = constraintAnnotation.message();
        startTime = constraintAnnotation.startTime();
        endTime = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        LocalTime start = getFieldValue(o, startTime);
        LocalTime end = getFieldValue(o, endTime);

        if (start == null || end == null) {
            return true;
        }

        if (start.isAfter(end)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(startTime)
                .addConstraintViolation();
            return false;
        }

        return true;
    }

    private LocalTime getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field dateField;
        try {
            dateField = clazz.getDeclaredField(fieldName);
            dateField.setAccessible(true);
            Object target = dateField.get(object);
            if (target instanceof LocalTime) {
                return (LocalTime) target;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
        return null;
    }
}

