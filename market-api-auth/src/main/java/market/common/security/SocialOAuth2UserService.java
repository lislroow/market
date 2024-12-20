package market.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import market.api.auth.entity.UserEntity;
import market.api.auth.producer.UserProducer;
import market.api.auth.repository.UserRepository;
import market.common.vo.SessionUser;
import market.common.vo.User;

@Service
@RequiredArgsConstructor
public class SocialOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final UserRepository userRepository;
  final UserProducer userProducer;
  final ModelMapper model;
  final BCryptPasswordEncoder bcryptPasswordEncoder;
  
  @Value("${social.user.defaultPassword}")
  private String defaultPassword;
  
  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User loadedUser = delegate.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    SocialOAuthAttributes attributes = SocialOAuthAttributes.of(registrationId, userNameAttributeName, loadedUser.getAttributes());
    UserEntity entity = userRepository.findByOauth2Id(attributes.getOauth2Id());
    if (ObjectUtils.isEmpty(entity)) {
      entity = attributes.toEntity();
      entity.setPassword("{bcrypt}" + bcryptPasswordEncoder.encode(defaultPassword));
      entity = userRepository.save(entity);
      userProducer.send(model.map(entity, User.class));
    }
    return new SessionUser(model.map(entity, User.class), attributes.getAttributes());
  }
}
