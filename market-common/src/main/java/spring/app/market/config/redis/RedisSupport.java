package spring.app.market.config.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSupport {

  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, Object> hashOps;
  
  private final ModelMapper modelMapper;
  
  public RedisSupport(RedisTemplate<String, Object> redisTemplate, ModelMapper modelMapper) {
    this.redisTemplate = redisTemplate;
    this.hashOps = redisTemplate.opsForHash();
    this.modelMapper = modelMapper;
  }
  
  public void setHash(String key, String hashKey, Object val) {
    hashOps.put(key, hashKey, val);
  }
  
  public <T> T getHash(String key, String hashKey, Class<T> type) {
    return (T) hashOps.get(key, hashKey);
  }
  
  public void deleteHash(String key, String hashKey) {
    if (hashKey != null && hashKey.indexOf("*") > -1) {
      Set<String> hkeySet = hashOps.keys(key);
      hkeySet.forEach(hkey -> hashOps.delete(key, hkey));
    } else {
      hashOps.delete(key, hashKey);
    }
  }
  
  public <T> List<T> getAll(String key, Class<T> type) {
    List<T> list = new ArrayList<>();
    for (Object object : hashOps.values(key)) {
      if (object == null) {
        list.add(null);
      } else {
        list.add(modelMapper.map(object, type));
      }
    }
    return list;
  }
  
  public boolean isEmpty(String key, String hashKey) {
    return hashOps.hasKey(key, hashKey);
  }
  
  public void remove(String key, String hashKey) {
    hashOps.delete(key, hashKey);
  }
}
