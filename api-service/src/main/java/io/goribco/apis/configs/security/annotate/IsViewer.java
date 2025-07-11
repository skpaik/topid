package io.goribco.apis.configs.security.annotate;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('ROLE_USER')")
@PreAuthorize("hasRole('VIEWER')")
public @interface IsViewer {
}