package market.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Uuid {
  
  private Uuid() {}
  
  public static String create() {
    StringBuilder buf = new StringBuilder();
    String uuid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    for (int i = uuid.length(); i < 14; i++) {
      buf.append("0");
    }
    return buf.toString() + uuid;
  }
}
