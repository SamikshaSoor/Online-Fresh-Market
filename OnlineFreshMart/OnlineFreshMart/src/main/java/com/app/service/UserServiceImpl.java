package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.AddUserRequest;
import com.app.dto.UserLoginRequest;
import com.app.entity.Address;
import com.app.entity.User;
import com.app.repository.AddressRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public User registerUser(AddUserRequest userRequest) {

		System.out.println("recieved request for REGISTER USER");
		System.out.println(userRequest);

		Address address = new Address();
		address.setCity(userRequest.getCity());
		address.setPincode(userRequest.getPincode());
		address.setStreet(userRequest.getStreet());
		Address addAddress = addressRepo.save(address);
		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhoneNo(userRequest.getPhoneNo());
		user.setPassword(userRequest.getPassword());
		user.setRole(userRequest.getRole());
		User addUser = userRepo.save(user);
		return addUser;
	}

	@Override
	public User loginUser(UserLoginRequest loginRequest) {
		System.out.println("recieved request for LOGIN USER");
		System.out.println(loginRequest);

		User user = new User();
		user = userRepo.findByEmailIdAndPasswordAndRole(loginRequest.getEmailId(), loginRequest.getPassword(),
				loginRequest.getRole());
		return user;

	}

	@Override
	public List<User> getAllDeliveryPersons() {
		System.out.println("recieved request for getting ALL Delivery Persons!!!");
		List<User> deliveryPersons = this.userRepo.findByRole("Delivery");
		return deliveryPersons;
	}

	@Override
	public List<User> getAllAdmin() {
		System.out.println("recieved request for getting all admin");
		List<User> admins = this.userRepo.findByRole("Admin");
		return admins;
	}

	@Override
	public List<User> getAllCustomer() {
		System.out.println("received for getting all customers");
		List<User> customers = this.userRepo.findByRole("Customer");
		return customers;
	}

	@Override
	public List<User> getAllSupplier() {
		System.out.println("received for getting all suppliers");
		List<User> suppliers = this.userRepo.findByRole("Supplier");
		return suppliers;
	}
}
