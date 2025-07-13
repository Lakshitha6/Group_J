package com.groupJ.socialmedia_app.service.impl;

import com.groupJ.socialmedia_app.dto.RegisterDTO;
import com.groupJ.socialmedia_app.dto.UserDTO;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.repository.UserRepository;
import com.groupJ.socialmedia_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent())
            throw new RuntimeException("Email already in use");

        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        User saved = userRepository.save(user);

        UserDTO returnDTO = new UserDTO();
        returnDTO.setId(saved.getId());
        returnDTO.setEmail(saved.getEmail());
        returnDTO.setFirstName(saved.getFirstName());
        returnDTO.setLastName(saved.getLastName());

        return returnDTO;
    }

}
