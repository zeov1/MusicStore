package ru.zeovl.musicstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.zeovl.musicstore.exceptions.InvalidUserException;
import ru.zeovl.musicstore.exceptions.UserNotFoundException;
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


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAllOrderedById() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User user, BindingResult bindingResult) {
        User userToBeUpdated = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        if (bindingResult.hasErrors()) {
            throw new InvalidUserException();
        }

        userToBeUpdated.setUsername(user.getUsername());
        userToBeUpdated.setRole(user.getRole());
        userToBeUpdated.setProviderId(user.getProviderId());
        userToBeUpdated.setProvider(user.getProvider());
        userToBeUpdated.setPassword(user.getPassword());
        userToBeUpdated.setEnabled(user.isEnabled());
        userToBeUpdated.setEmail(user.getEmail());

        userRepository.save(userToBeUpdated);
    }

    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
