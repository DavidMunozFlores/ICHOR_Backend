package com.erguidos.ichor.pre;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.erguidos.ichor.enums.Role;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface AuthenticatedOnly {
    Role value();
}
