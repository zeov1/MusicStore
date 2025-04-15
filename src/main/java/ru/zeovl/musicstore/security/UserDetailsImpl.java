package ru.zeovl.musicstore.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.zeovl.musicstore.models.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public LocalDate getDateJoined() {
        return user.getDateJoined();
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
        return true; // TODO
    }
}
