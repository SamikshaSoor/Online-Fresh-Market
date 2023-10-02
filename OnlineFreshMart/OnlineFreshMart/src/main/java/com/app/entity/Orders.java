package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "order_id")
	private String orderId;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int quantity;

	@Column(name = "order_date")
	private String orderDate;

	@Column(name = "delivery_status")
	private String deliveryStatus;
	
	@Column(name = "delivery_date")
	private String deliveryDate;
	
	@Column(name = "delivery_time")
	private String deliveryTime;
	
	@Column(name = "delivery_assigned")
	private String deliveryAssigned;
	
	@Column(name = "delivery_person_id")
	private int deliveryPersonId;

}
