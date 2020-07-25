package de.p7s1.qa.sevenfacette;


import java.util.List;

public class SeleniumConfig {
  private boolean autoClose;
  private String baseUrl;
  private String browserName;
  private List<String> capabilities;
  private List<String> chromeArgs;
  private String chromeBin;
  private boolean highlightBorder;
  private String highlightColor;
  private String highlightSize;
  private String highlightStyle;
  private String listenerClass;
  private Double pollingInterval;
  private String remoteUrl;
  private String reportDir;
  private List<Integer> screenSize;
  private boolean startMaximized;
  private int timeout;


  public boolean isAutoClose() {
    return autoClose;
  }

  public void setAutoClose(boolean autoClose) {
    this.autoClose = autoClose;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getBrowserName() {
    return browserName;
  }

  public void setBrowserName(String browserName) {
    this.browserName = browserName;
  }

  public List<String> getCapabilities() {
    return capabilities;
  }

  public void setCapabilities(List<String> capabilities) {
    this.capabilities = capabilities;
  }

  public List<String> getChromeArgs() {
    return chromeArgs;
  }

  public void setChromeArgs(List<String> chromeArgs) {
    this.chromeArgs = chromeArgs;
  }

  public String getChromeBin() {
    return chromeBin;
  }

  public void setChromeBin(String chromeBin) {
    this.chromeBin = chromeBin;
  }

  public boolean isHighlightBorder() {
    return highlightBorder;
  }

  public void setHighlightBorder(boolean highlightBorder) {
    this.highlightBorder = highlightBorder;
  }

  public String getHighlightColor() {
    return highlightColor;
  }

  public void setHighlightColor(String highlightColor) {
    this.highlightColor = highlightColor;
  }

  public String getHighlightSize() {
    return highlightSize;
  }

  public void setHighlightSize(String highlightSize) {
    this.highlightSize = highlightSize;
  }

  public String getHighlightStyle() {
    return highlightStyle;
  }

  public void setHighlightStyle(String highlightStyle) {
    this.highlightStyle = highlightStyle;
  }

  public String getListenerClass() {
    return listenerClass;
  }

  public void setListenerClass(String listenerClass) {
    this.listenerClass = listenerClass;
  }

  public Double getPollingInterval() {
    return pollingInterval;
  }

  public void setPollingInterval(Double pollingInterval) {
    this.pollingInterval = pollingInterval;
  }

  public String getRemoteUrl() {
    return remoteUrl;
  }

  public void setRemoteUrl(String remoteUrl) {
    this.remoteUrl = remoteUrl;
  }

  public String getReportDir() {
    return reportDir;
  }

  public void setReportDir(String reportDir) {
    this.reportDir = reportDir;
  }

  public List<Integer> getScreenSize() {
    return screenSize;
  }

  public void setScreenSize(List<Integer> screenSize) {
    this.screenSize = screenSize;
  }

  public boolean isStartMaximized() {
    return startMaximized;
  }

  public void setStartMaximized(boolean startMaximized) {
    this.startMaximized = startMaximized;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
