package market.common.constant;

public class Constant {
  
  private Constant() {}

  public static final String BASE_PACKAGE = "market";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";
  
  public static class DBMS {
    public static final String H2 = "h2";
    public static final String MARIA = "maria";
    public static final String ORACLE = "oracle";
    public static final String VERTICA = "vertica";
    public static final String POSTGRES = "postgres";
  }
  
  public static final String SQL_SESSION_FACTORY_BEAN = "SqlSessionFactoryBean";
  public static final String DAO = "Dao";

  public static final String X_TOKEN_ID = "X-TOKEN-ID";
  public static final String ACCESS_TOKEN = "ACCESS-TOKEN";
  public static final String REFRESH_TOKEN = "REFRESH-TOKEN";
  
  public static final String PUB_TOKEN_ID = "PUB-TOKEN-ID";
  public static final String PUB_CAPTCHA_ENC = "PUB-CAPTCHA-ENC";
  public static final String PUB_CAPTCHA_ANS = "PUB-CAPTCHA-ANS";
  
  public static final String UPLOAD_BASE = "/upload";
  public static final String UPLOAD_BASE_PRODUCT = UPLOAD_BASE+"/product";
  
}
