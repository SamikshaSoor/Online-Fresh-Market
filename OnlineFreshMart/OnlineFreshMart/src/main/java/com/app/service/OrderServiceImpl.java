package com.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.MyOrderResponse;
import com.app.dto.UpdateDeliveryStatusRequest;
import com.app.entity.Cart;
import com.app.entity.Orders;
import com.app.entity.Product;
import com.app.entity.User;
import com.app.repository.CartRepository;
import com.app.repository.OrderRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.app.utilities.Constants.DeliveryStatus;
import com.app.utilities.Constants.DeliveryTime;
import com.app.utilities.Constants.IsDeliveryAssigned;
import com.app.utilities.Helper;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private UserRepository userRepo;

	// Create orders for the given customer based on their cart contents
	@Override
	public void createOrderForCustomer(int userId) throws Exception {
		// Generate a unique order ID
		String orderId = Helper.getAlphaNumericOrderId();
		List<Cart> userCarts = cartRepo.findByUserId(userId);

		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		String formatDateTime = currentDateTime.format(formatter);

		for (Cart cart : userCarts) {
			// Check if the quantity is available in stock
			if (cart.getQuantity() <= productRepo.findById(cart.getProduct().getId()).get().getQuantity()) {
				 
				// Reduce the product quantity in stock based on the cart quantity
				Product product = productRepo.findById(cart.getProduct().getId()).get();
				
				product.setQuantity(
						productRepo.findById(cart.getProduct().getId()).get().getQuantity() - cart.getQuantity());
				productRepo.save(product);

				// Create a new Orders object for the order
				Orders order = new Orders();
				order.setOrderId(orderId);
				order.setUser(cart.getUser());
				order.setProduct(cart.getProduct());
				order.setQuantity(cart.getQuantity());
				order.setOrderDate(formatDateTime);
				order.setDeliveryDate(DeliveryStatus.PENDING.value());
				order.setDeliveryStatus(DeliveryStatus.PENDING.value());
				order.setDeliveryTime(DeliveryTime.DEFAULT.value());
				order.setDeliveryAssigned(IsDeliveryAssigned.NO.value());

				orderRepo.save(order);
				cartRepo.delete(cart);
			} else {
				throw new Exception("quantity not available");
			}

		}
	}

	// Retrieve orders for a specific user
	@Override
	public List<MyOrderResponse> getOrdersForUser(int userId) {
		// Retrieve orders associated with the user
		List<Orders> userOrder = orderRepo.findByUser_id(userId);

		// Create a list to store order data for response
		List<MyOrderResponse> orderDatas = new ArrayList<>();

		// Populate the response data with order details
		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());
			// Calculate total price based on order quantity and product price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If delivery person assigned then find the Delivery Person and set their
				// contact and name
				Optional<User> optionalDeliveryPerson = this.userRepo.findById(order.getDeliveryPersonId());
				User deliveryPerson = optionalDeliveryPerson.get();
				orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
				orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
			}

			orderDatas.add(orderData);
		}

		return orderDatas;
	}

	// Retrieve all orders
	@Override
	public List<MyOrderResponse> getAllOrders() {
		List<Orders> userOrder = orderRepo.findAll();

		// Create a list to store order data for response
		List<MyOrderResponse> orderDatas = new ArrayList<>();

		// Populate the response data with order details
		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());

			// Calculate total price based on order quantity and product price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Set user-related details
			orderData.setUserId(order.getUser().getId());
			orderData.setUserName(order.getUser().getFirstName());
			orderData.setUserPhone(order.getUser().getPhoneNo());
			orderData.setAddress(order.getUser().getAddress());

			
			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If delivery person assigned then find the Delivery Person and set their
				// contact and name
				Optional<User> optionalDeliveryPerson = this.userRepo.findById(order.getDeliveryPersonId());
				User deliveryPerson = optionalDeliveryPerson.get();
				orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
				orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
			}

			orderDatas.add(orderData);
		}

		return orderDatas;
	}

	// Retrieve orders by order ID
	@Override
	public List<MyOrderResponse> getOrdersByOrderId(String orderId) {
		List<Orders> userOrder = orderRepo.findByOrderId(orderId);

		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());

			// Calculate total price based on order quantity and product price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Set user-related details
			orderData.setUserId(order.getUser().getId());
			orderData.setUserName(order.getUser().getFirstName());
			orderData.setUserPhone(order.getUser().getPhoneNo());
			orderData.setAddress(order.getUser().getAddress());

			
			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If delivery person assigned then find the Delivery Person and set their
				// contact and name
				Optional<User> optionalDeliveryPerson = this.userRepo.findById(order.getDeliveryPersonId());
				User deliveryPerson = optionalDeliveryPerson.get();
				orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
				orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
			}

			orderDatas.add(orderData);
		}

		return orderDatas;
	}

	// Update delivery status for orders
	@Override
	public List<MyOrderResponse> updateOrderDeliveryStatus(UpdateDeliveryStatusRequest deliveryRequest) {
		List<Orders> orders = orderRepo.findByOrderId(deliveryRequest.getOrderId());

		// Update delivery status and other details for the selected orders
		for (Orders order : orders) {
			order.setDeliveryDate(deliveryRequest.getDeliveryDate());
			order.setDeliveryStatus(deliveryRequest.getDeliveryStatus());
			order.setDeliveryTime(deliveryRequest.getDeliveryTime());
			orderRepo.save(order);
		}

		List<Orders> userOrder = orderRepo.findByOrderId(deliveryRequest.getOrderId());

		// Return the updated order data for response
		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());

			// Calculate total price based on order quantity and product price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Set user-related details
			orderData.setUserId(order.getUser().getId());
			orderData.setUserName(order.getUser().getFirstName());
			orderData.setUserPhone(order.getUser().getPhoneNo());
			orderData.setAddress(order.getUser().getAddress());

			
			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If delivery person assigned then find the Delivery Person and set their
				// contact and name
				Optional<User> optionalDeliveryPerson = this.userRepo.findById(order.getDeliveryPersonId());
				User deliveryPerson = optionalDeliveryPerson.get();
				orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
				orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
			}

			orderDatas.add(orderData);
		}

		return orderDatas;
	}

	// Assign a delivery person to orders
	@Override
	public List<MyOrderResponse> assignDeliveryPersonForOrder(UpdateDeliveryStatusRequest deliveryRequest) {
		List<Orders> orders = orderRepo.findByOrderId(deliveryRequest.getOrderId());

		// Retrieve the selected delivery person
		User deliveryPerson = null;
		Optional<User> optionalDeliveryPerson = this.userRepo.findById(deliveryRequest.getDeliveryId());
		if (optionalDeliveryPerson.isPresent()) {
			deliveryPerson = optionalDeliveryPerson.get();
		}

		// Assign the selected delivery person to the orders
		for (Orders order : orders) {
			order.setDeliveryAssigned(IsDeliveryAssigned.YES.value());
			order.setDeliveryPersonId(deliveryRequest.getDeliveryId());
			orderRepo.save(order);
		}

		List<Orders> userOrder = orderRepo.findByOrderId(deliveryRequest.getOrderId());

		// Return the updated order data for response
		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());

			// Calculate total price based on order quantity and product price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Set user-related details
			orderData.setUserId(order.getUser().getId());
			orderData.setUserName(order.getUser().getFirstName());
			orderData.setUserPhone(order.getUser().getPhoneNo());
			orderData.setAddress(order.getUser().getAddress());
			
			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If a delivery person is assigned, fetch their details and set contact and
				// name
				User dPerson = null;
				Optional<User> optionalPerson = this.userRepo.findById(order.getDeliveryPersonId());
				dPerson = optionalPerson.get();
				orderData.setDeliveryPersonContact(dPerson.getPhoneNo());
				orderData.setDeliveryPersonName(dPerson.getFirstName());
			}

			orderDatas.add(orderData);
		}

		return orderDatas;
	}

	// Retrieve delivery orders for a specific delivery person
	@Override
	public List<MyOrderResponse> getDeliveryOrders(int deliveryPersonId) {
		System.out.println("request came for MY DELIVERY ORDER for USER ID : " + deliveryPersonId);
		// Retrieve the delivery person's details
		User person = null;
		Optional<User> optionalDeliveryPerson = this.userRepo.findById(deliveryPersonId);
		if (optionalDeliveryPerson.isPresent()) {
			person = optionalDeliveryPerson.get();
		}

		// Retrieve orders assigned to the delivery person
		List<Orders> userOrder = orderRepo.findByDeliveryPersonId(deliveryPersonId);

		// Create a list to store order data for response
		List<MyOrderResponse> orderDatas = new ArrayList<>();

		for (Orders order : userOrder) {
			// Create a MyOrderResponse object to store order details for response
			MyOrderResponse orderData = new MyOrderResponse();

			// Set various order details using data from the 'order' object
			orderData.setOrderId(order.getOrderId());
			orderData.setProductDescription(order.getProduct().getDescription());
			orderData.setProductName(order.getProduct().getTitle());
			orderData.setProductImage(order.getProduct().getImageName());
			orderData.setQuantity(order.getQuantity());
			orderData.setOrderDate(order.getOrderDate());
			orderData.setProductId(order.getProduct().getId());
			orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
			orderData.setDeliveryStatus(order.getDeliveryStatus());

			// Calculate total price based on order quantity and product price
			// and set the total price
			orderData.setTotalPrice(
					String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

			// Set user-related details
			orderData.setUserId(order.getUser().getId());
			orderData.setUserName(order.getUser().getFirstName());
			orderData.setUserPhone(order.getUser().getPhoneNo());
			orderData.setAddress(order.getUser().getAddress());
			
			// Check if the order has a delivery person assigned
			if (order.getDeliveryPersonId() == 0) {
				// If no delivery person assigned, mark with 'PENDING'
				orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
				orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
			} else {
				// If a delivery person is assigned, set their contact and name
				orderData.setDeliveryPersonContact(person.getPhoneNo());
				orderData.setDeliveryPersonName(person.getFirstName());
			}
			// Add the populated MyOrderResponse object to the list
			orderDatas.add(orderData);
		}

		return orderDatas;
	}

}
