package com.smbc.library.auth_service.service.iservice;

import com.smbc.library.auth_service.dto.ResponseDto;
import com.smbc.library.auth_service.dto.UserLoginDto;
import com.smbc.library.auth_service.dto.UserRegisterDto;

public interface UserService {
    /**
     * Register a new user data
     * @param userData username, password, and fullname (for membership data) 
     * @return A new user data wrapped in response DTO
     */
    ResponseDto<?> register(UserRegisterDto userData);

    /**
     * 
     * @return
     */
    ResponseDto<?> login(UserLoginDto userLogin);
}
