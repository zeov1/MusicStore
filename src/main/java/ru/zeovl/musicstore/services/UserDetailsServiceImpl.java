package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.repositories.UserRepository;
import ru.zeovl.musicstore.security.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            return new UserDetailsImpl(foundUser.get());
        } else {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
    }
}
