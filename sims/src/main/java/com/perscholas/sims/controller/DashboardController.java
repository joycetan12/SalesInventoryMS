package com.perscholas.sims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.perscholas.sims.model.Item;
import com.perscholas.sims.service.ItemService;
import com.perscholas.sims.service.SaleService;

@Controller
public class DashboardController {

	@Autowired
	SaleService saleService;
	
	@Autowired
	ItemService itemService;
	
	@GetMapping("/dashboard")
	public String displayDashboard(Model model) {
		List<Object[]> topObjList = saleService.getTop3SaleItems();
		for(Object[] obj: topObjList) {
			Item item = itemService.getItemById((Long) obj[0]).get();
			obj[0] = item.getName();
		}
		model.addAttribute("topItems", topObjList);
		
		List<Item> lowItemList = itemService.getAllLowInventory(10);
		model.addAttribute("lowItemList", lowItemList);
		
		return "dashboard";
	}
}
