package de.p7s1.qa.sevenfacette.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

public class YMLPropertySource extends AbstractPropertySource {


  private Properties properties;

  public YMLPropertySource(String filePath) {
    try(FileInputStream fis = new FileInputStream(filePath)) {
      properties.load(fis);
    } catch (IOException e) {
      properties = null;
    }
  }

  public YMLPropertySource(InputStream is) {
    try {
      Properties props = new Properties();
      props.load(is);
      properties = props;
    } catch (NullPointerException | IOException e) {
      properties = null;
    }
  }

  public static Optional<YMLPropertySource> from(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("File Path can't be null");
    }
    File file = new File(filePath);
    return from(file);
  }

  public static Optional<YMLPropertySource> from(File file) {
    if (file == null || !file.isFile())
      return Optional.empty();
    Yaml yaml = new Yaml();
    Properties properties = new Properties();
    Map<String,Object> objectMap = new HashMap<>();
    Map<String,Object> intermediateMap = new HashMap<>();
    int count = 0;
    try (Reader reader = new UnicodeReader(new FileInputStream(file))) {
      for (Object object : yaml.loadAll(reader)) {
        if (object != null) {
          intermediateMap.putAll(transformObjectToMap(object));
          if (intermediateMap.containsKey("Profile")) {
            if (intermediateMap.get("Profile").toString().contains(System.getenv("SEVENFACETTE_PROFILE").toString())) {
              objectMap.putAll(transformObjectToMap(object));
              break;
            }
          } else {
            objectMap.putAll(transformObjectToMap(object));
          }
        }
      }
      process(properties, objectMap);
    } catch (IOException ex) {
      // TODO: Add exception message...
    }
    return Optional.of(new YMLPropertySource(properties));
  }

  @SuppressWarnings({ "unchecked" })
  private static Map<String,Object> transformObjectToMap(Object messageToTransform) {
    return (Map<String,Object>) messageToTransform;
  }


  private static void process(Properties props, Map<String, Object> map) {
    props.putAll(getFlattenedMap(map));

  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> asMap(Object object) {
    Map<String, Object> result = new LinkedHashMap<>();
    Map<Object, Object> map = (Map<Object, Object>) object;
    map.forEach((key, value) -> {
      if (value instanceof Map) {
        value = asMap(value);
      }
      if (key instanceof CharSequence) {
        result.put(key.toString(), value);
      }
      else {
        // It has to be a map key in this case
        result.put("[" + key.toString() + "]", value);
      }
    });
    return result;
  }

  protected static final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
    Map<String, Object> result = new LinkedHashMap<>();
    buildFlattenedMap(result, source, null);
    return result;
  }

  private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source,
    @Nullable String path) {
    source.forEach((key, value) -> {
      if (StringUtils.hasText(path)) {
        if (key.startsWith("[")) {
          key = path + key;
        }
        else {
          key = path + '.' + key;
        }
      }
      if (value instanceof String) {
        result.put(key, value);
      }
      else if (value instanceof Map) {
        // Need a compound key
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) value;
        buildFlattenedMap(result, map, key);
      }
      else if (value instanceof Collection) {
        // Need a compound key
        @SuppressWarnings("unchecked")
        Collection<Object> collection = (Collection<Object>) value;
        if (collection.isEmpty()) {
          result.put(key, "");
        }
        else {
          int count = 0;
          for (Object object : collection) {
            buildFlattenedMap(result, Collections.singletonMap(
              "[" + (count++) + "]", object), key);
          }
        }
      }
      else {
        result.put(key, (value != null ? value : ""));
      }
    });
  }

  public String getConfigValue(String key) {
    return get(key);
  }


  public static Optional<YMLPropertySource> from(InputStream is) {
    if (is == null) {
      return Optional.empty();
    }
    try {
      Properties properties;
      properties = new Properties();
      properties.load(is);
      return Optional.of(new YMLPropertySource(properties));
    } catch (NullPointerException | IOException e) {
      return Optional.empty();
    }
  }

  public YMLPropertySource(Properties properties) {
    this.properties = properties;
  }

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
    return properties.getProperty(key);
  }
}
