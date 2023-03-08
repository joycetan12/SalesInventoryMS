package com.perscholas.sims.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	
	@NotEmpty(message = "Item name cannot be empty")
	@Column(unique = true, nullable = false)
	private String name;
	
	@NotEmpty(message = "Item description cannot be empty")
	@Column(nullable = false)
	private String description;
	
	@NotNull(message = "Item inventory cannot be null")
	@Min(value = 0, message = "Item inventory cannot be negative")
	@Column(nullable = false)
	private Integer inventory;
	
	@NotNull(message = "Item cost cannot be null")
	@Min(value = 0, message = "Item cost cannot be negative")
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal cost;
	
}
