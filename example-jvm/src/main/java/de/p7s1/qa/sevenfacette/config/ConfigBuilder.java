package de.p7s1.qa.sevenfacette.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ConfigBuilder {
  private List<ValueSource> psList = new ArrayList<>();

  /**
   * <p>Adds a property source whose values are provided by a {@link Map} object</p>
   * <p>The priority of the resultant {@link ValueSource} will have less priority to the ones
   * added by the previous calls to the variants of <code>addSource</code> methods, but more
   * priority to the sources added by the further calls to <code>addSource</code>.</p>
   * <p>If the resulting value of invoking {@link ValueSource#isAvailable()} is
   * <code>false</code>, the properties source won't be added to the chain of {@link ValueSource}</p>
   * @param map The source values for the properties, as a {@link Map}
   * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
   */
  public ConfigBuilder addSource(Map<String,?> map) {
    return addSource(new MapPropertySource(map));
  }
  /**
   * <p>Adds a property source whose values are provided by a {@link java.util.Properties} object</p>
   * <p>The priority of the resultant {@link ValueSource} will have less priority to the ones
   * added by the previous calls to the variants of <code>addSource</code> methods, but more
   * priority to the sources added by the further calls to <code>addSource</code>.</p>
   * <p>If the resulting value of invoking {@link ValueSource#isAvailable()} is
   * <code>false</code>, the properties source won't be added to the chain of {@link ValueSource}</p>
   * @param properties The source values for the properties, as a {@link java.util.Properties}
   * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
   */
  public ConfigBuilder addSource(Properties properties) {
    return addSource(new JavaUtilPropertySource(properties));
  }

  /**
   * <p>Adds a property source whose values are provided by a {@link ValueSource} object</p>
   * <p>The priority of the added {@link ValueSource} will have less priority to the ones
   * added by the previous calls to the variants of <code>addSource</code> methods, but more
   * priority to the sources added by the further calls to <code>addSource</code>.</p>
   * <p>If the resulting value of invoking {@link ValueSource#isAvailable()} is
   * <code>false</code>, the properties source won't be added to the chain of {@link ValueSource}</p>
   * @param propertySource The source values for the properties
   * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
   */
  public ConfigBuilder addSource(ValueSource propertySource) {
    if(propertySource.isAvailable()) {
      psList.add(propertySource);
    }
    return this;
  }

  /**
   * <p>Tries to add a {@link ValueSource} that may be contained into an {@link Optional}.</p>
   * <p>If the provided {@link Optional} is empty, the invocation of this method has no effect.</p>
   * <p>The priority of the added {@link ValueSource} will have less priority to the ones
   * added by the previous calls to the variants of <code>addSource</code> methods, but more
   * priority to the sources added by the further calls to <code>addSource</code>.</p>
   * <p>If the resulting value of invoking {@link ValueSource#isAvailable()} is
   * <code>false</code>, the properties source won't be added to the chain of {@link ValueSource}</p>
   * <p>This convenience method is provided for fluent usage with methods that
   * return an {@link Optional}, such as {@link JavaUtilPropertySource#from(String)}</p>
   * @param propertySourceOpt The source values for the properties
   * @return A reference same <code>ConfigurationBuilder</code> that has been invoked
   */
  public ConfigBuilder addSource(Optional<? extends ValueSource> propertySourceOpt) {
    if (propertySourceOpt.isPresent()) {
      ValueSource propertySource = propertySourceOpt.get();
      if (propertySource.isAvailable()) {
        psList.add(propertySource);
      }
    }
    return this;
  }

  /**
   * Builds a {@link Configurator} instance that accesses the added {@link ValueSource} according
   * to their given priority.
   * @return A new instance of {@link Configurator}
   * @throws ConfigurationException if no available property sources have been provided
   */
  public Configurator build() {
    if(psList.size() < 1 || psList.stream().filter(ValueSource::isAvailable).count() == 0) {
      throw new ConfigurationException("No available property sources provided");
    }
    return new Configurator(psList);
  }
}
