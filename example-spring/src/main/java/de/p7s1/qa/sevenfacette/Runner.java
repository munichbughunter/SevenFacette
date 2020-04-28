package de.p7s1.qa.sevenfacette;


import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig;
import de.p7s1.qa.sevenfacette.http.GenericHttpClient;
import de.p7s1.qa.sevenfacette.http.GenericHttpClientKt;
import de.p7s1.qa.sevenfacette.http.HttpClientFactory;
import de.p7s1.qa.sevenfacette.http.HttpHeader;
import de.p7s1.qa.sevenfacette.http.HttpResponse;
import de.p7s1.qa.sevenfacette.kafka.KConsumer;
import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class Runner implements CommandLineRunner {

  @Autowired
  @Qualifier("restfulbookerclient")
  private HttpClientConfig restfulBookerConfig;

  @Autowired
  @Qualifier("testclient")
  private HttpClientConfig testClientConfig;

  @Autowired
  @Qualifier("ingestConsumer")
  private KTableTopicConfig kafkaTableTopicConfiguration;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Loaded beans:");
    System.out.println(restfulBookerConfig.toString());

    GenericHttpClient httpClient = HttpClientFactory.createClient(restfulBookerConfig);
    HttpResponse response = httpClient.get("", new HttpHeader());
    System.out.println(response);
    System.out.println("--------------");
    System.out.println(testClientConfig.toString());
    System.out.println("--------------");
    System.out.println(kafkaTableTopicConfiguration.getKafkaTopic());
    KConsumer myConsumer = kafkaTableTopicConfiguration.createKConsumer(true);
    System.out.println("--------------");
    myConsumer.waitForKRecords(5000);
    System.out.println(myConsumer.getKRecordsCount());
    System.out.println(myConsumer.getLastKRecord());
    System.out.println("Finished");
  }
}
