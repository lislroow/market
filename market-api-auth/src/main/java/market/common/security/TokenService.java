package market.common.security;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.common.constant.Constant;
import market.common.dto.TokenResDto;
import market.common.enumcode.RESPONSE_CODE;
import market.common.exception.MarketException;
import market.common.redis.RedisSupport;
import market.common.vo.SessionUser;
import market.config.properties.JwtProperties;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {
  
  @Qualifier("redisSupportForAuthUser")
  final RedisSupport redisSupport;
  final JwtProperties jwtProperties;
  
  private Long refreshTokenExpTm;
  private Long accessTokenExpTm;
  
  private JWSSigner signer;
  private RSASSAVerifier verifier;

  @PostConstruct
  public void init() throws CertificateException, IOException {
    RSAPublicKey publicKey = loadRSAPublicKey(jwtProperties.getCert().getPublicKeyFilePath());
    this.verifier = new RSASSAVerifier(publicKey);
    
    RSAPrivateKey privateKey = loadRSAPrivateKey(jwtProperties.getCert().getPrivateKeyFilePath());
    this.signer = new RSASSASigner(privateKey);
    this.refreshTokenExpTm = jwtProperties.getToken().getRefreshTokenExpireTime();
    this.accessTokenExpTm = jwtProperties.getToken().getRefreshTokenExpireTime();
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
  
  private RSAPublicKey loadRSAPublicKey(String crtFilePath) throws CertificateException, IOException  {
    try (FileInputStream fis = new FileInputStream(crtFilePath)) {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
      return (RSAPublicKey) cert.getPublicKey();
    }
  }
  
  public String createToken(org.springframework.security.core.Authentication authentication) throws Exception {
    String email = ((SessionUser)authentication.getPrincipal()).getEmail();
    log.info("create token");
    String tokenId = null;
    try {
      tokenId = UUID.randomUUID().toString();
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      String token;
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(email)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + refreshTokenExpTm * 1000))
          .claim("scope", "user")
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      token = signedJWT.serialize();
      Duration ttl = Duration.ofSeconds(refreshTokenExpTm);
      this.redisSupport.setHash(tokenId, Constant.REFRESH_TOKEN, token, ttl); // refreshToken 의 expireTime 을 ttl 로 설정
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(email)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + accessTokenExpTm * 1000))
          .claim("scope", "user")
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      token = signedJWT.serialize();
      this.redisSupport.setHash(tokenId, Constant.ACCESS_TOKEN, token);
    } catch (Exception e) {
      log.error("message: {}", e.getMessage());
      throw new MarketException(RESPONSE_CODE.A001, e);
    }
    return tokenId;
  }
  
  public ResponseCookie createTokenCookie(org.springframework.security.core.Authentication authentication) throws Exception {
    String tokenId = this.createToken(authentication);
    return ResponseCookie.from(Constant.X_TOKEN_ID, tokenId)
        .path("/")
        .httpOnly(true)
        .build();
  }
  
  public TokenResDto.Verify verifyToken(String tokenId) throws ParseException, JOSEException {
    TokenResDto.Verify resDto = new TokenResDto.Verify();
    Object accessToken = this.redisSupport.getHash(tokenId, Constant.ACCESS_TOKEN);
    Assert.isTrue(accessToken != null, "accessToken not be null");
    Assert.isTrue(accessToken instanceof String, "accessToken type is java.lang.String");
    
    if (accessToken == null) {
      throw new MarketException(RESPONSE_CODE.A003);
    }
    
    SignedJWT signedJWT = SignedJWT.parse(accessToken.toString());
    if (signedJWT.verify(this.verifier)) {
      String email = signedJWT.getJWTClaimsSet().getSubject();
      resDto.setUserId(email);
    } else {
      throw new MarketException(RESPONSE_CODE.A002);
    }
    return resDto;
  }
}
