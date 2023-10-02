package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.AddToCartRequest;
import com.app.dto.CartDataResponse;
import com.app.dto.CartResponse;
import com.app.entity.Cart;
import com.app.entity.Product;
import com.app.entity.User;
import com.app.global_exceptions.ResourceNotFoundException;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductRepository productRepo;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// Method to add a product to the user's cart
	@Override
	public void addToCart(AddToCartRequest addToCartRequest) throws Exception {

		System.out.println("request came for ADD PRODUCT TO CART");
		System.out.println(addToCartRequest);

		// Retrieve user information based on user ID from the repository
		Optional<User> optionalUser = userRepo.findById(addToCartRequest.getUserId());
		System.out.println("in service" + optionalUser);
		User user = null;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		}

		// Retrieve product information based on product ID from the repository
		Optional<Product> optionalProduct = productRepo.findById(addToCartRequest.getProductId());
		// Check if the product quantity is available
		if (optionalProduct.get().getQuantity() >= addToCartRequest.getQuantity()) {
			Product product = null;

			if (optionalProduct.isPresent()) {
				product = optionalProduct.get();
			}
			// Create a cart item and set its details
			Cart cart = new Cart();
			cart.setProduct(product);
			cart.setQuantity(addToCartRequest.getQuantity());
			cart.setUser(user);
			cartRepo.save(cart);
		} else {
			// If the product quantity is not available
			throw new ResourceNotFoundException("Quantity not available");
		}

	}

	// Method to retrieve the user's cart data
	@Override
	public CartResponse myCart(int userId) throws JsonProcessingException {

		System.out.println("request came for MY CART for USER ID : " + userId);

		List<CartDataResponse> cartDatas = new ArrayList<>();

		List<Cart> userCarts = cartRepo.findByUserId(userId);

		double totalCartPrice = 0;

		for (Cart cart : userCarts) {
			// Create a CartDataResponse object and populate it with cart item details
			CartDataResponse cartData = new CartDataResponse();
			cartData.setCartId(cart.getId());
			cartData.setProductDescription(cart.getProduct().getDescription());
			cartData.setProductName(cart.getProduct().getTitle());
			cartData.setProductImage(cart.getProduct().getImageName());
			cartData.setQuantity(cart.getQuantity());
			cartData.setProductId(cart.getProduct().getId());

			cartDatas.add(cartData);
			double productPrice = Double.parseDouble(cart.getProduct().getPrice().toString());

			totalCartPrice = totalCartPrice + (cart.getQuantity() * productPrice);

		}
		// Create a CartResponse object and set its details
		CartResponse cartResponse = new CartResponse();
		cartResponse.setTotalCartPrice(String.valueOf(totalCartPrice));
		cartResponse.setCartData(cartDatas);
		// Convert the CartResponse object to a JSON string using the ObjectMapper
		// and print on screen to debug
		String json = objectMapper.writeValueAsString(cartResponse);

		System.out.println(json);
		return cartResponse;

	}

	// Method to remove an item from the user's cart
	@Override
	public void removeCartItem(int cartId) {

		System.out.println("request came for DELETE CART ITEM WHOSE ID IS : " + cartId);
		// Retrieve cart item based on cart ID from the repository and delete it
		Optional<Cart> optionalCart = this.cartRepo.findById(cartId);
		Cart cart = new Cart();

		if (optionalCart.isPresent()) {
			cart = optionalCart.get();
		}

		this.cartRepo.delete(cart);

	}

	// Method to clear all items from the user's cart
	@Override
	public void clearCart(int userId) {
		List<Cart> userCarts = cartRepo.findByUserId(userId);
		// Delete all cart items associated with the user
		cartRepo.deleteAll(userCarts);
	}
}