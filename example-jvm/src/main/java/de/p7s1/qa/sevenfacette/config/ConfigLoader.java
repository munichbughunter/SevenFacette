package de.p7s1.qa.sevenfacette.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import org.yaml.snakeyaml.Yaml;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConfigLoader {
  private ObjectMapper objectMapper = new ObjectMapper();

  private Yaml yaml = new Yaml();

  public JsonNode load(Reader... readers) throws JsonProcessingException, IOException {
    return load(null, Paths.get(""), readers);
  }

  public <T> T load(Class<T> targetType, Reader... readers) throws JsonProcessingException, IOException {
    return objectMapper.treeToValue(load(readers), targetType);
  }

  public JsonNode load(Path... paths) throws JsonProcessingException, IOException {
    JsonNode mainNode = null;

    for (Path path : paths) {
      try (Reader reader = Files.newBufferedReader(path)) {
        mainNode = load(mainNode, path.getParent(), reader);
      }
    }

    return mainNode;
  }

  public <T> T load(Class<T> targetType, Path... paths) throws JsonProcessingException, IOException {
    return objectMapper.treeToValue(load(paths), targetType);
  }

  private JsonNode load(JsonNode mainNode, Path directory, Reader... readers)
    throws JsonProcessingException, IOException {
    for (Reader reader : readers) {
      JsonNode thisNode = objectMapper.valueToTree(yaml.load(reader));
      for (JsonNode importField : thisNode.path("@import")) {
        JsonNode updateNode = load(directory.resolve(importField.asText()));
        mainNode = merge(mainNode, updateNode);
      }
      mainNode = merge(mainNode, thisNode);
    }
    return mainNode;
  }

  JsonNode merge(JsonNode mainNode, JsonNode updateNode) {
    if (mainNode == null || !mainNode.isObject() || !updateNode.isObject()) {
      return updateNode;
    }

    Iterator<String> fieldNames = updateNode.fieldNames();
    while (fieldNames.hasNext()) {
      String fieldName = fieldNames.next();
      JsonNode currentMainNode = mainNode.path(fieldName);
      JsonNode currentUpdateNode = updateNode.path(fieldName);
      if (currentMainNode.isObject() && currentUpdateNode.isObject()) {
        merge(currentMainNode, currentUpdateNode);
      } else if (mainNode.isObject()) {
        ((ObjectNode) mainNode).replace(fieldName, currentUpdateNode);
      }
    }
    return mainNode;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }
}
