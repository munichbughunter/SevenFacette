package de.p7s1.qa.sevenfacette.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.p7s1.qa.sevenfacette.http.config.ClientConfig;
import de.p7s1.qa.sevenfacette.http.config.ClientConfigs;

import java.io.File;
import java.io.IOException;

public class BookerConfig {

    public void loadConfig() throws IOException {

        File configFile = new File(getClass().getClassLoader().getResource("httpClientConfig.yml").getFile());
        System.out.println(configFile);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ClientConfigs configs = mapper.readValue(configFile, ClientConfigs.class);

        ClientConfig bookerClientConfig = configs.getHttpClients().stream().filter(config -> config.getName().equals("restfulBooker")).findAny().orElse(null);
        printClient(bookerClientConfig);

        ClientConfig testClientConfig = configs.getHttpClients().stream().filter(config -> config.getName().equals("testClient")).findAny().orElse(null);
        printClient(testClientConfig);

        ClientConfig noExists = configs.getHttpClients().stream().filter(config -> config.getName().equals("bla")).findAny().orElse(null);
        printClient(noExists);
    }

    public void printClient(ClientConfig config) {
        if(config == null) {
            System.out.println("No config found");
        } else {
            Url url = config.getUrl();
            System.out.println(url.create());
            System.out.println(config.getName());
            if(config.getProxy() != null) {
                System.out.println(config.getProxy());
            } else {
                System.out.println("No proxy used");
            }
            if(config.getAuthentication() != null) {
                TestBasicAuth auth = (TestBasicAuth) config.getAuthentication();
                System.out.println(auth.username);
                System.out.println(auth.password);
            } else {
                System.out.println("No Authentication");
            }
        }
    }
}
