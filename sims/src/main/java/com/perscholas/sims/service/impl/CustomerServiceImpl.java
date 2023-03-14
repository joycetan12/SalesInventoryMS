package com.perscholas.sims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.sims.model.Customer;
import com.perscholas.sims.repository.CustomerRepository;
import com.perscholas.sims.repository.SaleRepository;
import com.perscholas.sims.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findByOrderByFirstName();
	}

	public Optional<Customer> getCustomerById(Long custId) {
		return customerRepository.findById(custId);
	}
	
	public Optional<Customer> getCustomerByEmail(String email){
		return customerRepository.findByEmail(email);
	}

	public void addNewCustomer(Customer cust) {
		customerRepository.save(cust);	
	}

	public void updateCustomer(Customer updatedCust) {
		Optional<Customer> cust = customerRepository.findById(updatedCust.getId());
		
		if(cust.isPresent()) {
			Customer existingCust = cust.get();
			existingCust.setFirstName(updatedCust.getFirstName());
			existingCust.setLastName(updatedCust.getLastName());
			existingCust.setEmail(updatedCust.getEmail());
			customerRepository.save(existingCust);
		}
	}

	@Transactional
	public void deleteCustomerById(Long custId) {
		saleRepository.deleteByCustomerId(custId);
		customerRepository.deleteById(custId);
	}
	
}
