package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	@Column(name="first_name")
    private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="email_id")
    private String emailId;
	
	@JsonIgnore
	@Column(name="password")
    private String password;
	@Column(name="phone_no")
    private String phoneNo;
	@Column(name="role")
    private String role;
	
	@OneToOne
	@JoinColumn(name="address_id")
	private Address address;

	 
	
}
