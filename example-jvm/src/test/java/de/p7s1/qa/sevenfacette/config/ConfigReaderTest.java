package de.p7s1.qa.sevenfacette.config;

import org.junit.jupiter.api.Test;

public class ConfigReaderTest {
    @Test
    public void configReader() {
        ConfigReader reader = new ConfigReader();
        FacetteConfig config = reader.readConfig();
        System.out.println(config.getHttpClients().get(0).getName());

    }
}
