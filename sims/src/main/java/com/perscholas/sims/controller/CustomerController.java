package com.perscholas.sims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.perscholas.sims.model.Customer;
import com.perscholas.sims.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customers")
	public String displayCustomerPage(Model model) {
		List<Customer> allCustList = customerService.getAllCustomers();
		model.addAttribute("custList", allCustList);
		return "customers";
	}

	@GetMapping("/customers/create")
	public String createCustomerForm(Model model) {
		model.addAttribute("cust", new Customer());
		return "newCustomerForm";
	}

	@PostMapping("/customers")
	public String addNewCustomer(@ModelAttribute("cust") Customer cust) {
		customerService.addNewCustomer(cust);
		return "redirect:/customers";
	}

	@GetMapping("/customers/edit/{custId}")
	public String updateCustomerForm(Model model, @PathVariable Long custId) {
		model.addAttribute("cust", customerService.getCustomerById(custId).get());
		return "editCustomerForm";
	}

	@PostMapping("/customers/edit")
	public String updateCustomer(@ModelAttribute("cust") Customer custUpdate) {
		customerService.updateCustomer(custUpdate);
		return "redirect:/customers";
	}

	@GetMapping("/customers/{custId}")
	public String deleteCustomer(@PathVariable Long custId) {
		customerService.deleteCustomerById(custId);
		return "redirect:/customers";
	}

}