package market.api.security;

import org.modelmapper.ModelMapper;
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
import market.lib.vo.SessionUser;
import market.lib.vo.User;

@Service
@RequiredArgsConstructor
public class SocialOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final UserRepository userRepository;
  final UserProducer userProducer;
  final ModelMapper model;
  
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
      entity.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode("123"));
      entity = userRepository.save(entity);
      userProducer.send(model.map(entity, User.class));
    }
    return new SessionUser(model.map(entity, User.class), attributes.getAttributes());
  }
}
