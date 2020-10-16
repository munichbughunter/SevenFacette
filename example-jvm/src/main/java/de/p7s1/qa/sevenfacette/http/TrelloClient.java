package de.p7s1.qa.sevenfacette.http;


public class TrelloClient {
    private GenericHttpClient client;

    public TrelloClient() { this.client = HttpClientFactory.createClient("trello"); }

    public void getAllBoards() {
        HttpHeader header = new HttpHeader();

        HttpResponse response = client.get("/members/me/boards", header);
        System.out.println(response.getStatus());
        System.out.println(response.getBody());
    }
}
