package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.*;

public class GraphQlClient extends GenericHttpClient {
    private String basePath = "https://...";

    public GraphQlClient() {
        Url url = new Url().baseUrl(basePath);
        System.out.println(url.create());
        super.url(url);
    }


public HttpResponse getGraphQlContent(GraphQlContent content) {
        return this.postGraphQl("graphql", content, new HttpHeader(), false);
    }

}
