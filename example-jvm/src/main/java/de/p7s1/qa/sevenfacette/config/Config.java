package de.p7s1.qa.sevenfacette.config;

public class Config {
  /**
   * Specifies a default value
   * @param defaultByte the default value
   * @return the default value
   */
  public static byte def(byte defaultByte) {
    return defaultByte;
  }
  /**
   * Specifies a default value
   * @param defaultChar the default value
   * @return the default value
   */
  public static char def(char defaultChar) {
    return defaultChar;
  }
  /**
   * Specifies a default value
   * @param defaultInt the default value
   * @return the default value
   */
  public static int def(int defaultInt) {
    return defaultInt;
  }
  /**
   * Specifies a default value
   * @param defaultLong the default value
   * @return the default value
   */
  public static long def(long defaultLong) {
    return defaultLong;
  }
  /**
   * Specifies a default value
   * @param defaultFloat the default value
   * @return the default value
   */
  public static float def(float defaultFloat) {
    return defaultFloat;
  }
  /**
   * Specifies a default value
   * @param defaultDouble the default value
   * @return the default value
   */
  public static double def(double defaultDouble) {
    return defaultDouble;
  }
  /**
   * Specifies a default value
   * @param defaultBoolean the default value
   * @return the default value
   */
  public static boolean def(boolean defaultBoolean) {
    return defaultBoolean;
  }
  /**
   * Specifies a default value. This method is not needed
   * even for final static fields, but is kept for lexical consistency
   *
   * @param string the default value
   * @return the default value
   */
  public static String def(String string) {
    return string;
  }

  /**
   * Avoids final static values to be inlined with a 0 byte value
   * @return a 0 byte
   */
  public static byte aByte() {
    return 0;
  }
  /**
   * Avoids final static values to be inlined with a 0 integer value
   * @return a 0 integer value
   */
  public static int anInt() {
    return 0;
  }
  /**
   * Avoids final static values to be inlined with a 0 long value
   * @return a 0 long value
   */
  public static int aLong() {
    return 0;
  }
  /**
   * Avoids final static values to be inlined with a 0 float value
   * @return a 0 float value
   */
  public static int aFloat() {
    return 0;
  }
  /**
   * Avoids final static values to be inlined with a 0 double value
   * @return a 0 double value
   */
  public static double aDouble() {
    return 0;
  }
  /**
   * Avoids final static values to be inlined with a false boolean value
   * @return false
   */
  public static boolean aBoolean() {
    return false;
  }
  /**
   * Avoids final static values to be inlined with a '\0' char value
   * @return '\0'
   */
  public static char aChar() {
    return '\0';
  }
  /**
   * Avoids final static values to be inlined with a null String value
   * @return null String value
   */
  public static String aString() {
    return null;
  }
}
