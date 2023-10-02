package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddUserRequest;
import com.app.dto.UserLoginRequest;
import com.app.entity.User;
import com.app.service.UserService;

@RestController // When you annotate a class with @RestController, you're telling Spring that this class is responsible for handling incoming HTTP requests and returning HTTP responses
@RequestMapping("api/user")//annotation specifies the base URL //it means that requests starting with http://localhost:your-port/api/user will be handled by this controller. 
@CrossOrigin(origins = "http://localhost:3000")//annotation allows requests from a specific origin to access the resources provided by this controller
public class UserController {

    @Autowired //automatically create and inject instances of objects into another class.
    private UserService userService;

    // Endpoint to register a new user
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody AddUserRequest userRequest) {
        try {
            User addUser = userService.registerUser(userRequest); // Call the service to register the user
            return ResponseEntity.ok(addUser); // Return the registered user with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
        }
    }

    // Endpoint to authenticate and log in a user
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest); // Call the service to log in the user
            if (user != null) {
                return ResponseEntity.ok(user); // Return the logged in user with OK status
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging in.");
        }
    }

    // Endpoint to get all delivery persons
    @GetMapping("deliveryperson/all")
    public ResponseEntity<?> getAllDeliveryPersons() {
        try {
            List<User> deliveryPersons = userService.getAllDeliveryPersons(); // Call the service to get all delivery persons
            return ResponseEntity.ok(deliveryPersons); // Return the delivery persons with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting delivery persons.");
        }
    }

    // Endpoint to get all admin users
    @GetMapping("admin/all")
    public ResponseEntity<?> getAllAdmin() {
        try {
            List<User> admins = userService.getAllAdmin(); // Call the service to get all admin users
            return ResponseEntity.ok(admins); // Return the admin users with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting admin users.");
        }
    }

    // Endpoint to get all customer users
    @GetMapping("customer/all")
    public ResponseEntity<?> getAllCustomer() {
        try {
            List<User> customers = userService.getAllCustomer(); // Call the service to get all customer users
            return ResponseEntity.ok(customers); // Return the customer users with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting customer users.");
        }
    }

    // Endpoint to get all supplier users
    @GetMapping("supplier/all")
    public ResponseEntity<?> getAllSupplier() {
        try {
            List<User> suppliers = userService.getAllSupplier(); // Call the service to get all supplier users
            return ResponseEntity.ok(suppliers); // Return the supplier users with OK status
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting supplier users.");
        }
    }
}
