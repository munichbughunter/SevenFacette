package de.p7s1.qa.sevenfacette.config;

public class ConfigurationException extends RuntimeException {
  /**
   * {@inheritDoc}
   */
  public ConfigurationException() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  public ConfigurationException(String message) {
    super(message);
  }

  /**
   * {@inheritDoc}
   */
  public ConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * {@inheritDoc}
   */
  public ConfigurationException(Throwable cause) {
    super(cause);
  }

  /**
   * {@inheritDoc}
   */
  protected ConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
