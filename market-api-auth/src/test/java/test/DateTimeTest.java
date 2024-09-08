package test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateTimeTest {

  @Test
  public void convertTime() {
    System.out.println(toStr(1717052518));
  }
  
  private String toStr(long time) {
    if (time < 1e12) {
      time *= 1e3;
    }
    String str = null;
    LocalDateTime localDateTime = Instant
        .ofEpochMilli(time)             // long(epochMilli) -> Instant
        .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
        .toLocalDateTime();             // ZonedDateTime -> LocalDateTime
    str = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
    return str;
  }
}
