package de.p7s1.qa.sevenfacette.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class FileLoaderTest {

  static final String RESOURCES_TEST_FOLDER = "testfiles/";

  @Test
  @Disabled
  void readRessourceFileAsInputStream() {
    InputStream inputStream = new FileLoader().loadFileFromResourceAsStream(RESOURCES_TEST_FOLDER, "test.txt");

    String result = new BufferedReader(new InputStreamReader(inputStream)).lines()
      .parallel().collect(Collectors.joining("\n"));

    assert(!result.isEmpty());
  }

  @Test
  @Disabled
  void readRessourceFileAsString() {
    String fileContent = new FileLoader().loadFileFromResourceAsString(RESOURCES_TEST_FOLDER, "test.txt");
    assert(!fileContent.isEmpty());
  }
}
