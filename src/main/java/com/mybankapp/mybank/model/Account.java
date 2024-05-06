package com.mybankapp.mybank.model;



import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

	@Id
	@UuidGenerator
	private String id;
	
	private Double balance;
	private Currency currency;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
