package de.p7s1.qa.sevenfacette.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

public class YMLProcessor {
  private ResolutionMethod resolutionMethod = ResolutionMethod.OVERRIDE;

  private Resource[] resources = new Resource[0];

  private List<DocumentMatcher> documentMatchers = Collections.emptyList();

  private boolean matchDefault = true;

  public void setDocumentMatchers(DocumentMatcher... matchers) {
    this.documentMatchers = Arrays.asList(matchers);
  }

  public void setMatchDefault(boolean matchDefault) {
    this.matchDefault = matchDefault;
  }

  public void setResolutionMethod(ResolutionMethod resolutionMethod) {

    //Assert.notNull(resolutionMethod, "ResolutionMethod must not be null");
    this.resolutionMethod = resolutionMethod;
  }

  public void setResources(String file) {

  }

  public void setResources(Resource... resources) {
    this.resources = resources;
  }

  public void process(InputStream inputStream) {
    Yaml yaml = createYaml();
    boolean found = process(yaml, inputStream);
    if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND && found) {
      return;
    }

  }

  protected Yaml createYaml() {
    LoaderOptions options = new LoaderOptions();
    options.setAllowDuplicateKeys(false);
    return new Yaml(options);
  }



  private boolean process(Yaml yaml, InputStream inputStream) {
    int count = 0;
    try {
      try (Reader reader = new UnicodeReader(inputStream)) {
        for (Object object : yaml.loadAll(reader)) {
          if (object != null && process(asMap(object))) {
            count++;
            if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND) {
              break;
            }
          }
        }
      }
    }
    catch (IOException ex) {
      System.out.println("ERROR");
    }
    return (count > 0);
  }

  private void handleProcessError(Resource resource, IOException ex) {
    if (this.resolutionMethod != ResolutionMethod.FIRST_FOUND &&
      this.resolutionMethod != ResolutionMethod.OVERRIDE_AND_IGNORE) {
      throw new IllegalStateException(ex);
    }
  }


  @SuppressWarnings("unchecked")
  private Map<String, Object> asMap(Object object) {
    // YAML can have numbers as keys
    Map<String, Object> result = new LinkedHashMap<>();
    if (!(object instanceof Map)) {
      // A document can be a text literal
      result.put("document", object);
      return result;
    }

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


  private boolean process(Map<String, Object> map) {
    Properties properties = CollectionFactory.createStringAdaptingProperties();
    properties.putAll(getFlattenedMap(map));

    if (this.documentMatchers.isEmpty()) {
      return true;
    }

    MatchStatus result = MatchStatus.ABSTAIN;
    for (DocumentMatcher matcher : this.documentMatchers) {
      MatchStatus match = matcher.matches(properties);
      result = MatchStatus.getMostSpecific(match, result);
      if (match == MatchStatus.FOUND) {
        //callback.process(properties, map);
        return true;
      }
    }

    if (result == MatchStatus.ABSTAIN && this.matchDefault) {
      //callback.process(properties, map);
      return true;
    }
    return false;
  }

  protected final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
    Map<String, Object> result = new LinkedHashMap<>();
    buildFlattenedMap(result, source, null);
    return result;
  }

  private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, @Nullable String path) {
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

  public interface MatchCallback {

    /**
     * Process the given representation of the parsing results.
     * @param properties the properties to process (as a flattened
     * representation with indexed keys in case of a collection or map)
     * @param map the result map (preserving the original value structure
     * in the YAML document)
     */
    void process(Properties properties, Map<String, Object> map);
  }


  public interface DocumentMatcher {

    /**
     * Test if the given properties match.
     * @param properties the properties to test
     * @return the status of the match
     */
    MatchStatus matches(Properties properties);
  }

  public enum MatchStatus {

    /**
     * A match was found.
     */
    FOUND,

    /**
     * No match was found.
     */
    NOT_FOUND,

    /**
     * The matcher should not be considered.
     */
    ABSTAIN;

    /**
     * Compare two {@link MatchStatus} items, returning the most specific status.
     */
    public static MatchStatus getMostSpecific(MatchStatus a, MatchStatus b) {
      return (a.ordinal() < b.ordinal() ? a : b);
    }
  }

  public enum ResolutionMethod {

    /**
     * Replace values from earlier in the list.
     */
    OVERRIDE,

    /**
     * Replace values from earlier in the list, ignoring any failures.
     */
    OVERRIDE_AND_IGNORE,

    /**
     * Take the first resource in the list that exists and use just that.
     */
    FIRST_FOUND
  }
}
