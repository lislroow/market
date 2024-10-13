package market.api.security;

import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.api.config.JwtProperty;
import market.lib.constant.Constant;
import market.lib.dto.auth.TokenResDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {
  
  final RedisTemplate<String, String> redisTemplate;
  final JwtProperty jwtProperty;
  private JWSSigner signer;

  @PostConstruct
  public void init() throws Exception {
    // 키 생성
    /*
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(2048);
    KeyPair keyPair = keyGen.generateKeyPair();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    */
    RSAPrivateKey privateKey = loadRSAPrivateKey(jwtProperty.getCert().getPrivateKeyFilePath());
    this.signer = new RSASSASigner(privateKey);
  }
  
  private RSAPrivateKey loadRSAPrivateKey(String keyFilePath) throws IOException {
    try (FileReader keyReader = new FileReader(keyFilePath);
          PEMParser pemParser = new PEMParser(keyReader)) {
      Object object = pemParser.readObject();
      PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
      
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
      PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
      
      return (RSAPrivateKey) privateKey;
    }
  }
  
  public String createToken(String userId) throws Exception {
    log.info("create token");
    String tokenId = null;
    try {
      tokenId = UUID.randomUUID().toString();
      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(userId)
          .issuer(jwtProperty.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + jwtProperty.getToken().getAccessTokenExpireTime() * 1000)) // 1시간 후 만료
          .claim("scope", "user")
          .build();
      SignedJWT signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      String token = signedJWT.serialize();
      if (log.isDebugEnabled()) System.out.println(tokenId + " " + token);
      redisTemplate.opsForHash().put(tokenId, Constant.ACCESS_TOKEN, token); // , Duration.ofMillis(5000)
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(userId)
          .issuer(jwtProperty.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + jwtProperty.getToken().getRefreshTokenExpireTime() * 1000)) // 1시간 후 만료
          .claim("scope", "user")
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      if (log.isDebugEnabled()) System.out.println(tokenId + " " + token);
      redisTemplate.opsForHash().put(tokenId, Constant.REFRESH_TOKEN, token);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return tokenId;
  }
  
  public ResponseCookie createTokenCookie(String userId) throws Exception {
    String tokenId = this.createToken(userId);
    ResponseCookie cookie = ResponseCookie.from(Constant.X_TOKEN_ID, tokenId)
        .path("/")
        .httpOnly(true)
        .build();
    return cookie;
  }
  
  public TokenResDto.Verify verifyToken(String tokenId) throws Exception {
    TokenResDto.Verify resDto = new TokenResDto.Verify();
    Object accessToken = redisTemplate.opsForHash().get(tokenId, Constant.ACCESS_TOKEN);
    resDto.setUserId("myeonggu.kim");
    return resDto;
  }
}
