package ru.nsu.bolotov.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ZeroArgs {
    int NUMBER_OF_ARGS = 0;
}
