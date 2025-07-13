package com.groupJ.socialmedia_app.service;

import com.groupJ.socialmedia_app.dto.RegisterDTO;
import com.groupJ.socialmedia_app.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(RegisterDTO registerDTO);
}
