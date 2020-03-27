package de.p7s1.qa.sevenfacette.config;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ConfigClass {
  @JsonProperty boolean banner;
  @JsonProperty String profile;
  @JsonProperty String user;
  @JsonProperty String time;
  @JsonProperty String warning;
}
