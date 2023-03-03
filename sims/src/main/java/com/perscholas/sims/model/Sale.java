package com.perscholas.sims.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date_of_sale")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
	private Date dateOfSale;
	
	@ManyToOne(targetEntity = Item.class)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal salePrice;
	
	@Column(nullable = false)
	private boolean paid;
	
}
