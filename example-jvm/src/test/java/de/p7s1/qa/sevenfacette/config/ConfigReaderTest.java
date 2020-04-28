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
        assert(config.getSevenFacette().getHttp().getClients().size() == 2);
        assert(config.getSevenFacette().getCustom().size() == 4);
        assert(config.getSevenFacette().getCustom().get("testImport1").equals("imported Value"));
        assert(config.getSevenFacette().getCustom().get("test3").equals("imported Value for Field test3"));
    }

    @Test
    public void checkConfigObject() {
        System.clearProperty("FACETTE_CONFIG");
        FacetteConfig.INSTANCE.update();
        assert(FacetteConfig.INSTANCE.getHttp().getClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 0);
    }

    @Test
    public void checkConfigObjectMultiFiles() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml");
        FacetteConfig.INSTANCE.update();
        assert(FacetteConfig.INSTANCE.getHttp().getClients().size() == 2);
        assert(FacetteConfig.INSTANCE.getCustom().size() == 4);
        assert(FacetteConfig.INSTANCE.getCustom().get("testImport1").equals("imported Value"));
        assert(FacetteConfig.INSTANCE.getCustom().get("test3").equals("imported Value for Field test3"));
    }
}
