package com.example.demo.securingweb;

import org.springframework.stereotype.Repository;

@Repository
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getAccount(String username) {
        return userRepository.findByUsername(username);
    }
}
