package com.perscholas.sims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

@Controller
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CustomerService customerService;
	
	@ModelAttribute("itemList")
	public List<Item> allItemList(){
		return itemService.getAllItems();
	}
	
	@ModelAttribute("custList")
	public List<Customer> allCustList(){
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/sales")
	public String displaySalesPage(Model model){
		List<Sale> allSalesList = saleService.getAllSales();
		model.addAttribute("saleList", allSalesList);
		return "sales";
	}
	
	@GetMapping("/sales/create")
    public String createSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        return "newSaleForm";
    }
	
	@PostMapping("/sales")
	public String addNewSale(@ModelAttribute("sale") @Valid Sale sale, BindingResult result) {
		
		if(sale.getQuantity() != null && sale.getQuantity() > sale.getItem().getInventory()) {
			result.rejectValue("quantity", null, "Quantity of sale is larger than available inventory for item");
		}
		
		if(sale.getSalePrice() != null && sale.getSalePrice().compareTo(sale.getItem().getCost()) < 0) {
			result.rejectValue("salePrice", null, "Sale price of item is lower than cost of item");
		}
			
		if(result.hasErrors()) {
			return "newSaleForm";
		}
		
		saleService.addNewSale(sale);
		return "redirect:/sales";
	}
	
	@GetMapping("/sales/edit/{saleId}")
	public String editSaleForm(@PathVariable Long saleId, Model model) {
		model.addAttribute("sale", saleService.getSaleById(saleId).get());
		return "editSaleForm";
	}

	@PostMapping("/sales/edit")
	public String updateSale(@ModelAttribute("sale") @Valid Sale updatedSale, BindingResult result) {
		
		Sale existingSale = saleService.getSaleById(updatedSale.getId()).get();
		
		if(existingSale.getItem() == updatedSale.getItem()) {
			if(updatedSale.getQuantity() != null && updatedSale.getQuantity() > (updatedSale.getItem().getInventory() + existingSale.getQuantity())) {
				result.rejectValue("quantity", null, "Quantity of sale is larger than available inventory for item");
			}
		}
		else {
			if(updatedSale.getQuantity() != null && updatedSale.getQuantity() > updatedSale.getItem().getInventory()) {
				result.rejectValue("quantity", null, "Quantity of sale is larger than available inventory for item");
			}
		}
		
		if(updatedSale.getSalePrice() != null && updatedSale.getSalePrice().compareTo(updatedSale.getItem().getCost()) < 0) {
			result.rejectValue("salePrice", null, "Sale price of item is lower than cost of item");
		}
			
		if(result.hasErrors()) {
			return "editSaleForm";
		}
		
		saleService.updateSale(updatedSale);
		return "redirect:/sales";
	}
	
	@GetMapping("/sales/delete/{saleId}")
	public String deleteSale(@PathVariable Long saleId) {
		saleService.deleteSaleById(saleId);
		return "redirect:/sales";
	}
}
