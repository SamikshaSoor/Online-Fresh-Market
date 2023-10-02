package com.app.dto;

import com.app.entity.Address;

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
public class MyOrderResponse {
	
	private String orderId;
	
    private int productId;
    
    private int userId;
    
    private String userName;  
    
    private Address address;
    
    private String userPhone;
	
	private String productName;
	
	private String productDescription;
	
	private String productImage;
	
	private int quantity;
	
	private String totalPrice;
	
	private String orderDate;
	
	private String deliveryDate;
	
	private String deliveryStatus;
	
	private String deliveryPersonName;
	
	private String deliveryPersonContact;

}
