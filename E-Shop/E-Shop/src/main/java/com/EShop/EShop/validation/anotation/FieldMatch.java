package com.EShop.EShop.validation.anotation;

import com.EShop.EShop.validation.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

import static com.EShop.EShop.constants.ValidationMessage.THE_FIELDS_MUST_MATCH_EX_MSG;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
public @interface FieldMatch {

    String message() default THE_FIELDS_MUST_MATCH_EX_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String first();

    String second();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @interface List {
        FieldMatch[] value();
    }
}
