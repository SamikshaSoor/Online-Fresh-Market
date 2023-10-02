package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDataResponse {

	private int cartId;

	private int productId;

	private String productName;

	private String productDescription;

	private String productImage;

	private int quantity;

}
