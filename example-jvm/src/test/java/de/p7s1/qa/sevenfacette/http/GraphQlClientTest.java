package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.GraphQlContent;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GraphQlClientTest {

    @Test
    public void sendGraphQlRequest() throws IOException {
        String resource = "/graphql/graphqlRequest.graphql";
        System.out.println(this.getClass().getResource(resource));

        InputStream input = this.getClass().getResourceAsStream(resource);
        String content = IOUtils.toString(input, StandardCharsets.UTF_8);

        GraphQlClient client = new GraphQlClient();
        HttpResponse response = client.getGraphQlContent(new GraphQlContent(content));
        System.out.println(response.getStatus());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }

}
