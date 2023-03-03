package com.perscholas.sims.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Integer inventory;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal cost;
	
//	@ManyToOne(targetEntity=Item.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private User user_id;
	
}
