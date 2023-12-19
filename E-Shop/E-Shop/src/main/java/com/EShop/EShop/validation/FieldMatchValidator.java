package com.EShop.EShop.validation;

import com.EShop.EShop.validation.anotation.FieldMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;




public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;


    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            Object first = BeanUtils.getPropertyDescriptor(value.getClass(),firstFieldName);
            Object second = BeanUtils.getPropertyDescriptor(value.getClass(),secondFieldName);

            valid = first == null && second == null || first != null && first.equals(second);
        }catch (Exception ignore){

        }
        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
