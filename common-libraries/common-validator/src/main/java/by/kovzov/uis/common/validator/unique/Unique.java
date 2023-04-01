package by.kovzov.uis.common.validator.unique;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is used to mark field that should be unique
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
}
