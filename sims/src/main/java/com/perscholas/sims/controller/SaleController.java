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
import com.perscholas.sims.model.Item;
import com.perscholas.sims.model.Sale;
import com.perscholas.sims.service.CustomerService;
import com.perscholas.sims.service.ItemService;
import com.perscholas.sims.service.SaleService;

@Controller
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/sales")
	public String displaySalesPage(Model model){
		List<Sale> allSalesList = saleService.getAllSales();
		model.addAttribute("saleList", allSalesList);
		return "sales";
	}
	
	@GetMapping("/sales/create")
    public String createSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("itemList", itemList);
        List<Customer> custList = customerService.getAllCustomers();
        model.addAttribute("custList", custList);
        return "newSaleForm";
    }
	
	@PostMapping("/sales")
	public String addNewSale(@ModelAttribute("sale") Sale sale) {
		saleService.addNewSale(sale);
		return "redirect:/sales";
	}
	
	@GetMapping("/sales/edit/{saleId}")
	public String editSaleForm(@PathVariable Long saleId, Model model) {
		model.addAttribute("sale", saleService.getSaleById(saleId).get());
		List<Item> itemList = itemService.getAllItems();
        model.addAttribute("itemList", itemList);
        List<Customer> custList = customerService.getAllCustomers();
        model.addAttribute("custList", custList);
		return "editSaleForm";
	}

	@PostMapping("/sales/edit")
	public String updateSale(@ModelAttribute("sale") Sale sale) {
		saleService.updateSale(sale);
		return "redirect:/sales";
	}
	
	@GetMapping("/sales/delete/{saleId}")
	public String deleteSale(@PathVariable Long saleId) {
		saleService.deleteSaleById(saleId);
		return "redirect:/sales";
	}
}
