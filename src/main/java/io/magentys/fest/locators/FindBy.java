package io.magentys.fest.locators;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FindBy {

    public static final String defaultMemoryKey = "latest.frame";
    public static final String defaultText = "";

    Class<? extends Component> clazz();

    String attributes() default defaultText;

    String rememberAs() default defaultMemoryKey;
}
