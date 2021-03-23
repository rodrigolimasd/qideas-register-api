package com.rodtech.qideasregisterapi.service.impl;

import com.rodtech.qideasregisterapi.exception.RegisteredEmailException;
import com.rodtech.qideasregisterapi.exception.UserNotFoundException;
import com.rodtech.qideasregisterapi.model.User;
import com.rodtech.qideasregisterapi.repository.UserRepository;
import com.rodtech.qideasregisterapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGISTERED = "E-mail already registered";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String id) {
        return findUserById(id);
    }

    @Override
    public User findByEmail(String email) {
        return findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User create(User user) {
        saveUserValidation(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User userDb = findUserById(user.getId());
        //TODO: more information to update
        userDb.setEmail(user.getEmail());

        saveUserValidation(userDb);
        userRepository.save(userDb);
        return null;
    }

    @Override
    public void delete(String id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    private void saveUserValidation(User user){
        findUserByEmail(user.getEmail())
                .ifPresent(userDb -> {
                    if(user.getId()==null || !user.getId().equals(userDb.getId()))
                        throw new RegisteredEmailException(EMAIL_REGISTERED);
                });
    }

    private User findUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Optional<User> findUserByEmail(String email){
        return userRepository.findFirstByEmail(email);
    }
}
