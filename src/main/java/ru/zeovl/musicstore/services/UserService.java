package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.repositories.UserRepository;

import java.util.List;

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


    public List<User> findAllOrderedById() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
