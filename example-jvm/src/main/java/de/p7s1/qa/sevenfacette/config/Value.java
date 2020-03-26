package de.p7s1.qa.sevenfacette.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Value {
  /**
   * The names of the property that provide the value to the field that this annotation targets
   * @return The names of the property that provide the value to the field that this annotation targets
   */
  String[] value();
}
