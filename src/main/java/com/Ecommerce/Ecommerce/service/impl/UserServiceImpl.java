package com.Ecommerce.Ecommerce.service.impl;

import com.Ecommerce.Ecommerce.DTOs.LoginRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.DTOs.UserDto;
import com.Ecommerce.Ecommerce.entity.User;
import com.Ecommerce.Ecommerce.service.interf.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class UserServiceImpl implements UserService {

    @Override
    public Response registerUser(UserDto registrationRequest) {
        return null;
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public Response getAllUsers() {
        return null;
    }

    @Override
    public User getLoginUser() {
        return null;
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        return null;
    }
}
