package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.repositories.UserRepository;
import ru.zeovl.musicstore.security.AuthProvider;
import ru.zeovl.musicstore.security.UserDetailsImpl;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {


    private final UserRepository userRepository;

    @Autowired
    public OAuth2UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        AuthProvider provider = AuthProvider.valueOf(
                request.getClientRegistration().getRegistrationId().toUpperCase()
        );
        return processOAuth2User(provider, oAuth2User);
    }

    private OAuth2User processOAuth2User(AuthProvider provider, OAuth2User oAuth2User) {
        String email = provider == AuthProvider.YANDEX
                ? oAuth2User.getAttribute("default_email")
                : oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(provider, oAuth2User));

        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(AuthProvider provider, OAuth2User oAuth2User) {
        User user = new User();
        user.setProvider(provider);
        user.setEmail(
                provider == AuthProvider.YANDEX
                        ? oAuth2User.getAttribute("default_email")
                        : oAuth2User.getAttribute("email")
        );
        user.setProviderId(
                provider == AuthProvider.GOOGLE
                        ? oAuth2User.getAttribute("sub")
                        : oAuth2User.getAttribute("id")
        );
        user.setUsername("user_" + user.getProvider() + "_" + user.getProviderId());
        return userRepository.save(user);
    }
}