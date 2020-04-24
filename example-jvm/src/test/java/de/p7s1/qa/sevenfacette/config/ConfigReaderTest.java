package de.p7s1.qa.sevenfacette.config;

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig;
import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig;
import org.junit.jupiter.api.Test;

public class ConfigReaderTest {
    @Test
    public void configReaderTest() {
        System.clearProperty("FACETTE_CONFIG");
        ConfigReader reader = new ConfigReader();
        SevenFacetteConfig config = reader.readConfig();
        assert(config.getSevenFacette().getHttp().getClients().size() == 2);
    }

    @Test
    public void multiFileConfigReaderTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");
        ConfigReader reader = new ConfigReader();
        SevenFacetteConfig config = reader.readConfig();
        System.out.println(config.getSevenFacette().getHttp().getClients().size());
        assert(config.getSevenFacette().getHttp().getClients().size() == 2);
    }

    @Test
    public void checkConfigObject() {
        System.clearProperty("FACETTE_CONFIG");
        assert(FacetteConfig.INSTANCE.getHttp().getClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 0);
    }

    @Test
    public void checkConfigObjectMultiFiles() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");
        assert(FacetteConfig.INSTANCE.getHttp().getClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 0);
    }
}
