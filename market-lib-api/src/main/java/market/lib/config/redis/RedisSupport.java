package market.lib.config.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisSupport {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ModelMapper modelMapper;
  
  // hash
  public void setHash(String key, String hashKey, Object val) {
    this.redisTemplate.opsForHash().put(key, hashKey, val);
  }
  
  @SuppressWarnings("unchecked")
  public <T> T getHash(String key, String hashKey, Class<T> type) {
    return (T) this.redisTemplate.opsForHash().get(key, hashKey);
  }
  
  public void deleteHash(String key, String hashKey) {
    if (hashKey != null && hashKey.indexOf("*") > -1) {
      Set<Object> hkeySet = this.redisTemplate.opsForHash().keys(key);
      hkeySet.forEach(hkey -> this.redisTemplate.opsForHash().delete(key, hkey));
    } else {
      this.redisTemplate.opsForHash().delete(key, hashKey);
    }
  }
  
  public <T> List<T> getHashAll(String key, Class<T> type) {
    List<T> list = new ArrayList<>();
    for (Object object : this.redisTemplate.opsForHash().values(key)) {
      if (object == null) {
        list.add(null);
      } else {
        list.add(modelMapper.map(object, type));
      }
    }
    return list;
  }
  
  public boolean isHashEmpty(String key, String hashKey) {
    return this.redisTemplate.opsForHash().hasKey(key, hashKey);
  }
  
  public void removeHash(String key, String hashKey) {
    this.redisTemplate.opsForHash().delete(key, hashKey);
  }
}
