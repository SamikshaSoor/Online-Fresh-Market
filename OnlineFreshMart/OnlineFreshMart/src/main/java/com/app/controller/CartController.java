package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddToCartRequest;
import com.app.dto.CartResponse;
import com.app.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("api/user/")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

	@Autowired
	private CartService cartService;

	// Endpoint to add items to the cart
	@PostMapping("cart/add")
	public ResponseEntity<?> add(@RequestBody AddToCartRequest addToCartRequest) {
		System.out.println(addToCartRequest.getQuantity());// Print the quantity of items being added
		try {
			cartService.addToCart(addToCartRequest);// Call the service to add items to the cart
		} catch (Exception e) {
			e.getMessage();// Handle any exceptions that might occur during the process
		}
		return new ResponseEntity<>(HttpStatus.OK);// Return a response with OK status
	}

	 // Endpoint to retrieve items from the cart for a specific user
	@GetMapping("mycart")
	public ResponseEntity<CartResponse> getMyCart(@RequestParam("userId") int userId) throws JsonProcessingException {
		CartResponse cartResponse = cartService.myCart(userId);// Call the service to get cart information for the user
		return new ResponseEntity<CartResponse>(cartResponse, HttpStatus.OK);// Return cart information with OK status
	}

	// Endpoint to remove a specific item from the cart
	@DeleteMapping("mycart/remove")
	public ResponseEntity<String> removeCartItem(@RequestParam("cartId") int cartId) {
		cartService.removeCartItem(cartId);// Call the service to remove the specified item from the cart
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);// Return success message with OK status
	}

	// Endpoint to clear the entire cart for a specific user
	@DeleteMapping("clearcart")
	public ResponseEntity<String> clearCart(@RequestParam("userId") int userId) {
		cartService.clearCart(userId);// Call the service to clear the entire cart for the user
		return new ResponseEntity<>("Cart cleared successfully", HttpStatus.OK);// Return success message with OK status
	}
}
