package com.perscholas.sims.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.perscholas.sims.model.Customer;
import com.perscholas.sims.repository.CustomerRepository;
import com.perscholas.sims.repository.SaleRepository;

@SpringBootTest
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@MockBean
	private SaleRepository saleRepository;
	
	@Test
	public void getAllCustomersTest() {
		Customer c1 = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Customer c2 = new Customer(200L, "Sophia", "Stafford", "sophia@gmail.com");
		Customer c3 = new Customer(300L, "Damon", "Warren", "damon@gmail.com");
		when(customerRepository.findByOrderByFirstName()).thenReturn(Stream.of(c1, c2, c3).collect(Collectors.toList()));
		Assertions.assertEquals(3, customerService.getAllCustomers().size());
	}
	
	@Test 
	public void getCustomerByIdSuccessTest() {
		Customer c = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		when(customerRepository.findById(100L)).thenReturn(Optional.of(c));
		Assertions.assertTrue(customerService.getCustomerById(100L).isPresent());	
	}
	
	@Test
	public void getCustomerByIdFailTest() {
		when(customerRepository.findById(100L)).thenReturn(Optional.empty());
		Assertions.assertTrue(customerService.getCustomerById(100L).isEmpty());
	}
	
	@Test 
	public void getCustomerByEmailSuccessTest() {
		Customer c = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		when(customerRepository.findByEmail("brett@gmail.com")).thenReturn(Optional.of(c));
		Assertions.assertTrue(customerService.getCustomerByEmail("brett@gmail.com").isPresent());	
	}
	
	@Test
	public void getCustomerByEmailFailTest() {
		when(customerRepository.findByEmail("brett@gmail.com")).thenReturn(Optional.empty());
		Assertions.assertTrue(customerService.getCustomerByEmail("brett@gmail.com").isEmpty());
	}
	
	@Test
	public void addNewCustomerTest() {
		Customer c = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		customerService.addNewCustomer(c);
		verify(customerRepository, times(1)).save(c);
	}
	
	@Test
	public void updateCustomerTest() {
		Customer c = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Customer updated = new Customer(100L, "Brett", "Parsons", "brettparsons@gmail.com");
		when(customerRepository.findById(100L)).thenReturn(Optional.of(c));
		customerService.updateCustomer(updated);
		verify(customerRepository, times(1)).save(any());
	}
	
	@Test
	public void deleteCustomerByIdTest() {
		customerService.deleteCustomerById(111L);
		verify(saleRepository, times(1)).deleteByCustomerId(111L);
		verify(customerRepository, times(1)).deleteById(111L);
	}
}
