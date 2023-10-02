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
public class UpdateDeliveryStatusRequest {
	
	private String orderId;
	
	private String deliveryStatus;
	
	private String deliveryTime;
	
	private String deliveryDate;
	
	private int deliveryId;   

}
