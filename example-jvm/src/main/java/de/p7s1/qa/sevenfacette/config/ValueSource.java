package de.p7s1.qa.sevenfacette.config;

public interface ValueSource {
  <T> T get(String name, Class<T> type);
  String getValue(String name);
  /**
   * Returns whether the property source is available. For example, a property source is <em>unavailable</em>
   * if the file that contains the properties has not been correctly read, or the object that stores the
   * property sources (e.g. a {@link java.util.Map}) is <code>null</code>.
   * @return <code>true</code> if the property source is available. <code>false</code> otherwise
   */
  boolean isAvailable();
}
