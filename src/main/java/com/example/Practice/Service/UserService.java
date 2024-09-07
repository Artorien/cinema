package com.example.Practice.Service;

import com.example.Practice.Repository.UserRepository;
import com.example.Practice.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void setImage(MultipartFile imageFile, User user) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageByteArray = imageFile.getBytes();
            user.setPfp(imageByteArray);
            userRepository.save(user);
        }
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    public boolean isTaken(String username) {
        return userRepository.existsByUsername(username);
    }
}