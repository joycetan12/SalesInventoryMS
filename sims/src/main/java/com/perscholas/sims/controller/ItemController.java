package com.perscholas.sims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.perscholas.sims.model.Item;
import com.perscholas.sims.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/inventory")
	public String displayInventoryPage(Model model){
		List<Item> allItemList = itemService.getAllItems();
		model.addAttribute("itemList", allItemList);
		return "inventory";
	}
	
	@GetMapping("/inventory/create")
    public String createItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "newItemForm";
    }
	
	@PostMapping("/inventory")
	public String addNewItem(@ModelAttribute("item") Item item) {
		itemService.addNewItem(item);
		return "redirect:/inventory";
	}
	
	@GetMapping("/inventory/edit/{itemId}")
    public String editItemForm(Model model, @PathVariable Long itemId) {
		model.addAttribute("item", itemService.getItemById(itemId).get());
        return "editItemForm";
    }
	
	@PostMapping("/inventory/edit")
	public String editItem(@ModelAttribute("item") Item item) {
		itemService.updateItem(item);
		return "redirect:/inventory";
	}
	
	@GetMapping("/inventory/delete/{itemId}")
	public String deleteItem(@PathVariable Long itemId) {
		itemService.deleteItemById(itemId);
		return "redirect:/inventory";
	}
	
}
