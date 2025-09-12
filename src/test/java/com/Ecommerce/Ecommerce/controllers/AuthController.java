package com.Ecommerce.Ecommerce.controllers;

import com.Ecommerce.Ecommerce.DTOs.LoginRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.DTOs.UserDto;
import com.Ecommerce.Ecommerce.service.interf.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller

public class AuthController {

    private UserService userServiceRepo;
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest){
        System.out.println(registrationRequest);
        return ResponseEntity.ok(userServiceRepo.registerUser(registrationRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userServiceRepo.loginUser(loginRequest));
    }
}
