package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
