package de.p7s1.qa.sevenfacette.config;

import org.junit.jupiter.api.Test;

public class ConfigReaderTest {
    @Test
    public void configReaderTest() {
        System.clearProperty("FACETTE_CONFIG");
        ConfigReader reader = new ConfigReader();
        FacetteConfigDataClass config = reader.readConfig();
        assert(config.getHttpClients().size() == 2);
    }

    @Test
    public void multiFileConfigReaderTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");
        ConfigReader reader = new ConfigReader();
        FacetteConfigDataClass config = reader.readConfig();
        System.out.println(config.getHttpClients().size());
        assert(config.getHttpClients().size() == 2);
    }

    @Test
    public void checkConfigObject() {
        System.clearProperty("FACETTE_CONFIG");
        assert(FacetteConfig.INSTANCE.getHttpClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 0);
    }

    @Test
    public void checkConfigObjectMultiFiles() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");
        assert(FacetteConfig.INSTANCE.getHttpClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 0);
    }
}
