package com.perscholas.sims.service;

import java.util.List;
import java.util.Optional;

import com.perscholas.sims.model.Customer;

public interface CustomerService {

	List<Customer> getAllCustomers();
	Optional<Customer> getCustomerById(Long custId);
	Optional<Customer> getCustomerByEmail(String email);
	void addNewCustomer(Customer cust);
	void updateCustomer(Customer updatedCust);
	void deleteCustomerById(Long custId);
	
}
