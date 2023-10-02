package com.app.service;

import java.util.List;

import com.app.dto.AddUserRequest;
import com.app.dto.UserLoginRequest;
import com.app.entity.User;

public interface UserService {
	User registerUser(AddUserRequest userRequest);
    User loginUser(UserLoginRequest loginRequest);
    List<User> getAllDeliveryPersons();
    List<User> getAllAdmin();
    List<User> getAllCustomer();
    List<User> getAllSupplier();
}
