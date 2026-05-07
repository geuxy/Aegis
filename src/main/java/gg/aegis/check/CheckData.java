package gg.aegis.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckData {

    String name();
    String unique();
    CheckType type();
}
