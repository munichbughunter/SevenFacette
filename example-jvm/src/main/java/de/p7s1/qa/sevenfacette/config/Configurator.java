package de.p7s1.qa.sevenfacette.config;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Configurator {
  private List<ValueSource> sources;

  /**
   * Instantiates a configurator
   *
   * @param sources a list of {@link ValueSource} instances, in order of priority
   */
  Configurator(List<ValueSource> sources) {
    this.sources = sources;
  }

  /**
   * <p>Configures the object passed as argument. It looks for {@link Value} annotations in the
   * object passed as parameter and assigns the annotated property name present in the
   * {@link ValueSource} with the hightes priority.</p>
   *
   * <p>The invocation of this method will also configure the static fields of the class that
   * the destination object belongs to.</p>
   *
   * @param dst the object to configure
   * @throws ConfigurationException if an invalid assignment has been intended (for example, assign
   *                               an alphanumeric value into an integer) or if the access to the destination field is
   *                               enforced by Java language access control and the underlying field is either inaccessible
   *                               or final.
   */
  public void configure(Object dst) {
    // Configure properties for this class and its superclasses
    for (Class c = dst.getClass(); c != null; c = c.getSuperclass()) {
      configure(dst, c);
    }
  }

  /**
   * Configures the static fields of a class passed as an argument, in a similar manner to
   * {@link #configure(Object)}
   *
   * @param clazz The class whose static fields have to be configured
   */
  public void configure(Class<?> clazz) {
    while (clazz != null) {
      configure(null, clazz);
      clazz = clazz.getSuperclass();
    }
  }

  private void configure(Object dst, Class clazz) {
    boolean areModifiersAccessible = true;
    // TODO: if a security manager is present, make this not failing when the accesses to fields are permitted

    Field modifiersField = null;
    try {
      modifiersField = Field.class.getDeclaredField("modifiers");
      areModifiersAccessible = modifiersField.isAccessible();
      modifiersField.setAccessible(true);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }

    for (Field f : clazz.getDeclaredFields()) {
      int fieldModifiers = f.getModifiers();
      if (dst != null || Modifier.isStatic(fieldModifiers)) {

        // Make accessible final static members
        boolean modifiersChanged = false;
        try {
          if (Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers)) {
            modifiersField.setInt(f, fieldModifiers & ~Modifier.FINAL);
            modifiersChanged = true;
          }
        } catch (IllegalAccessException e) {
          throw new ConfigurationException(e);
        }

        Stream.of(f.getAnnotations())
          .filter(a -> a.annotationType().isAssignableFrom(Value.class))
          .map(a -> (Value) a)
          .findFirst()
          .ifPresent(p ->
            findPriorValue(p.value(), f.getType())
              .ifPresent(value -> {
                boolean isAcessible = f.isAccessible();
                if (!isAcessible) {
                  f.setAccessible(true);
                }


                try {
                  f.set(dst, value);
                } catch (NumberFormatException | IllegalAccessException e) {
                  throw new ConfigurationException(e);
                }
                if (!isAcessible) {
                  f.setAccessible(false);
                }
              })
          );

        // Restore final static members
        if (modifiersChanged) {
          try {
            modifiersField.setInt(f, fieldModifiers);
          }
          catch(IllegalAccessException e){
            throw new ConfigurationException(e);
          }
        }

      }
    }
    modifiersField.setAccessible(areModifiersAccessible);
  }

  public String getConfigValue(String key) {
    return sources.get(0).getValue(key);
  }

  // finds any of the keys into the properties sources, returning the values by priority
  private <T> Optional<T> findPriorValue(String[] keys, Class<T> pType) {
    return sources.stream()
      .map(ps -> {
        for (String key: keys) {
          T type = ps.get(key, pType);
          if (type != null) {
            return type;
          }
        }
        return null;
      })
      .filter(value -> value != null)
      .findFirst();
  }

}
