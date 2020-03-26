package de.p7s1.qa.sevenfacette.config;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {
  InputStream getInputStream() throws IOException;
}
