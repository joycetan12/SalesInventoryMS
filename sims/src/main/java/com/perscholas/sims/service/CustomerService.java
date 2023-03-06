package com.perscholas.sims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.sims.model.Customer;
import com.perscholas.sims.repository.CustomerRepository;
import com.perscholas.sims.repository.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

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

	public void addNewCustomer(Customer cust) {
		customerRepository.save(cust);	
	}

	public void updateCustomer(Customer custUpdate) {
		Optional<Customer> existingCust = customerRepository.findById(custUpdate.getId());
		
		if(existingCust.isPresent()) {
			Customer updatedCust = existingCust.get();
			updatedCust.setFirstName(custUpdate.getFirstName());
			updatedCust.setLastName(custUpdate.getLastName());
			updatedCust.setEmail(custUpdate.getEmail());
			customerRepository.save(updatedCust);
		}
	}

	@Transactional
	public void deleteCustomerById(Long custId) {
		saleRepository.deleteByCustomerId(custId);
		customerRepository.deleteById(custId);
	}
	
}
