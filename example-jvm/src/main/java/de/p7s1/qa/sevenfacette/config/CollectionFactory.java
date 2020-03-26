package de.p7s1.qa.sevenfacette.config;

import java.util.Properties;
import org.jetbrains.annotations.Nullable;

public class CollectionFactory {
  @SuppressWarnings("serial")
  public static Properties createStringAdaptingProperties() {
    return new SortedProperties(false) {
      @Override
      @Nullable
      public String getProperty(String key) {
        Object value = get(key);
        return (value != null ? value.toString() : null);
      }
    };
  }
}
