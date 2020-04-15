package de.p7s1.qa.sevenfacette.config;

import org.junit.jupiter.api.Test;

public class ConfigReaderTest {
    @Test
    public void configReaderTest() {
        System.clearProperty("FACETTE_CONFIG");
        ConfigReader reader = new ConfigReader();
        FacetteConfig config = reader.readConfig();
        assert(config.getHttpClients().size() == 2);
    }

    @Test
    public void multiFileConfigReaderTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");

        ConfigReader reader = new ConfigReader();
        FacetteConfig config = reader.readConfig();
        System.out.println(config.getHttpClients().size());
        assert(config.getHttpClients().size() == 2);
    }
}
