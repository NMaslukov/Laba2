package com.sysprog.laba.annotation;

import com.sysprog.laba.enums.Stage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BotPath {
    String path() default "";

    Stage requiredStage() default Stage.ANY;
}
