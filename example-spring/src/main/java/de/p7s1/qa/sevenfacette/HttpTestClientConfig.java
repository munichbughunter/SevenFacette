package de.p7s1.qa.sevenfacette;

import de.p7s1.qa.sevenfacette.http.HttpProxy;
import de.p7s1.qa.sevenfacette.http.Url;
import java.util.HashMap;
import java.util.Map;


public class HttpTestClientConfig {

  private int connectionTimeout;
  private int connectionRequestTimeout;
  private int socketTimeout;
  private String protocol;
  private String baseUrl;
  private String urlPath;
  private String proxyHost;
  private int proxyPort;
  private int port;
  private Map<String, String> authMap = new HashMap<>();
  private HttpProxy httpProxy;

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public int getConnectionRequestTimeout() {
    return connectionRequestTimeout;
  }

  public void setConnectionRequestTimeout(int connectionRequestTimeout) {
    this.connectionRequestTimeout = connectionRequestTimeout;
  }

  public int getSocketTimeout() {
    return socketTimeout;
  }

  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setUrlPath(String urlPath) {
    this.urlPath = urlPath;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setProxyPort(int proxyPort) {
    this.proxyPort = proxyPort;
  }

  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public void setAuthenticationType(String authenticationType) {
    this.authMap.put("type", authenticationType);
  }

  public void setPassword(String password) {
    this.authMap.put("password", password);
  }

  public void setUserName(String userName) {
    this.authMap.put("username", userName);
  }

  public Map<String, String> getAuthentication() {
    return authMap;
  }

  public HttpProxy getHttpProxy() {
    httpProxy = new HttpProxy();
    httpProxy.setPort(this.proxyPort);
    httpProxy.setHost(this.proxyHost);
    return httpProxy;
  }

  public Url getUrl() {
    Url httpUrl = new Url();
    //httpUrl.url(this.protocol + this.baseUrl + ":" + this.port + this.urlPath);
    httpUrl.url(this.protocol + this.baseUrl);
    //httpUrl.url(this.protocol + this.baseUrl);
    return httpUrl;
  }
}
