package com.app.service;

import com.app.dto.AddToCartRequest;
import com.app.dto.CartResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CartService {
	public void addToCart(AddToCartRequest addToCartRequest) throws Exception;

	public CartResponse myCart(int userId) throws JsonProcessingException;

	public void removeCartItem(int cartId);
	
	public void clearCart(int userId);
}
