package test;

import java.util.Base64;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtTest {
  
  @Test
  public void decodeBase64() {
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY3MTk2NzgzNTFhNWZhZWRjMmU3MDI3NGJiZWE2MmRhMmE4YzRhMTIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjU2NjA1Mjk4OTktNzcwdWJqdWR0bGJnMGZncTQ3cTc2OGt1NW4wM3VrdXAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjU2NjA1Mjk4OTktNzcwdWJqdWR0bGJnMGZncTQ3cTc2OGt1NW4wM3VrdXAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDg1NjgxNTMzODY5MDM0MDg3NDkiLCJlbWFpbCI6Im1na2ltLm5ldEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IjNxTkpPcklRYnlUMXFpZXJscG1Ib2ciLCJuYW1lIjoi66y066qF7JmVIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0xJY3paNDM4NEFfakpVS0Z5MnUzeWc3Si1FR3d3d1JqZGtnZnJIb1E3VF9IMkVsU1U9czk2LWMiLCJnaXZlbl9uYW1lIjoi66y066qF7JmVIiwiaWF0IjoxNzE3MDQ4OTE4LCJleHAiOjE3MTcwNTI1MTh9.Mh1WHL3wFJJg0tVvdljhVnf6l8EqhKUx1yOOdAJsIg61T6K4cuDyCVCtZUniAiKozyaFn6awXcQcom2BAM5saXEGrhyeAuQ1WEGeacmXYYJ1K4dGtC6eCtYEBIIGIblMMGJ6cKFLVlXquvL_V5aZtSl7XoFNotjjBX5rpMRtzzvXwPrdkeZyltX9Wty1BIoAi8cBa-SLNYMnOPfmvL1vJD7eLfqSO3u5qNWspOaqP5_njuSkGbScXNcU1RqI_-nwsRetFuoQaVitG1hKVoNnpP5X-XOS2rEqfbghDp2cdlItquB3ip9NJt1Rs-vW-8HVkVODXALb9UEXs89D2p4OAw";
    if (token.contains("{id_token=")) {
      token = token.substring("{id_token=".length());
    }
    if (token.endsWith("}")) {
      token = token.substring(0, token.length()-1);
    }
    String encodeStr = null;
    if (token.contains(".")) {
      encodeStr = token.split("\\.")[1];
    } else {
      encodeStr = token;
    }
    String decodeStr = new String(Base64.getDecoder().decode(encodeStr));
    System.out.println(decodeStr);
    
    if (decodeStr.startsWith("{") && decodeStr.endsWith("}")) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        Map<String, Object> map = mapper.readValue(decodeStr, java.util.Map.class);
        map.forEach((key, value) -> {
          System.out.println(String.format("%s=%s", key, value));
        });
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }
  
}
