package market.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Uuid {
  
  public static String create() {
    String uuid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    for (int i = uuid.length(); i < 14; i++) {
      uuid = '0' + uuid;
    }
    return uuid;
  }
}
