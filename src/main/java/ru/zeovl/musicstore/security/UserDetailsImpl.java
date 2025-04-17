package ru.zeovl.musicstore.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.zeovl.musicstore.models.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private final User user;
    private final Map<String, Object> oauth2Attributes;

    public UserDetailsImpl(User user) {
        this.user = user;
        this.oauth2Attributes = getAttributes();
    }

    public UserDetailsImpl(User user, Map<String, Object> oauth2Attributes) {
        this.user = user;
        this.oauth2Attributes = oauth2Attributes;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public LocalDate getDateJoined() {
        return user.getDateJoined();
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (oauth2Attributes == null)
            return Map.of(
                    "email", user.getEmail(),
                    "provider", user.getProvider(),
                    "sub", user.getProviderId(),
                    "id", user.getProviderId(),
                    "enabled", user.isEnabled(),
                    "role", user.getRole(),
                    "dateJoined", user.getDateJoined()
            );
        else
            return oauth2Attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
