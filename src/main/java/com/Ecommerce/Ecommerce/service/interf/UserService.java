package com.Ecommerce.Ecommerce.service.interf;
import com.Ecommerce.Ecommerce.DTOs.LoginRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.DTOs.UserDto;
import com.Ecommerce.Ecommerce.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
