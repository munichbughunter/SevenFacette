package de.p7s1.qa.sevenfacette.config;

import java.util.Map;

public class MapPropertySource extends AbstractPropertySource {
  private Map<String, ?> properties;

  /**
   * <p>Instantiates the class by providing a map with the property values</p>
   * <p>If the properties argument is null, the {@link #isAvailable()} method will
   * always return {@code false}</p>
   * @param properties The properties to access later
   */
  public MapPropertySource(Map<String, ?> properties) {
    this.properties = properties;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAvailable() {
    return properties != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String get(String key) {
    if (!isAvailable()) {
      return null;
    }
    Object val = properties.get(key);
    return val == null ? null : val.toString();
  }
}
