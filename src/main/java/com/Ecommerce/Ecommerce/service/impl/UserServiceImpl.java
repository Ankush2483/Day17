package com.Ecommerce.Ecommerce.service.impl;

import com.Ecommerce.Ecommerce.DTOs.LoginRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.DTOs.UserDto;
import com.Ecommerce.Ecommerce.entity.User;
import com.Ecommerce.Ecommerce.enums.UserRole;
import com.Ecommerce.Ecommerce.mapper.EntityDtoMapper;
import com.Ecommerce.Ecommerce.repositories.UserRepo;
import com.Ecommerce.Ecommerce.security.JwtUtils;
import com.Ecommerce.Ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EntityDtoMapper entityDtoMapper;
    private final AuthenticationManager authenticationManager;


    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;
        if (registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();
        User savedUser = userRepo.save(user);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDto)
                .build();
    }



    @Override
    public Response getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDto = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();
        return Response.builder()
                .status(200)
                .userList(userDto)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            log.warn("No authenticated user found in SecurityContext");
            throw new UsernameNotFoundException("No authenticated user found");
        }
        String email = authentication.getName();
        log.info("User Email is: " + email);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User Not found with email: " + email));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);
        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            log.info("UserDetails found: {}", userDetails.getUsername());
            JwtUtils jwtUtils = new JwtUtils();
            String token = jwtUtils.generateToken(userDetails.getUsername());
            return Response.builder()
                    .status(200)
                    .message("User Successfully Logged In")
                    .token(token)
                    .expirationTime("24 Hours")
                    .role(userDetails.getAuthorities().iterator().next().getAuthority())
                    .build();
        } else {
            log.error("User not of type UserDetails. It's of type: {}", principal.getClass());
            throw new RuntimeException("User not of the expected type UserDetails.");
        }


    }
}
