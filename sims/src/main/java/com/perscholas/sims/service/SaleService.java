package com.perscholas.sims.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.sims.model.Item;
import com.perscholas.sims.model.Sale;
import com.perscholas.sims.repository.ItemRepository;
import com.perscholas.sims.repository.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	

	public List<Sale> getAllSales() {
		return saleRepository.findByOrderByDateOfSaleDesc();
	}

	public Optional<Sale> getSaleById(Long saleId) {
		return saleRepository.findById(saleId);
	}

	public void addNewSale(Sale sale) {
		Item existingItem = itemRepository.findById(sale.getItem().getId()).get();
		
		if(sale.getQuantity() <= existingItem.getInventory()) {
			int newCount = existingItem.getInventory() - sale.getQuantity();
			existingItem.setInventory(newCount);
			itemRepository.save(existingItem);
			saleRepository.save(sale);
		}
		else {
			// TODO: need to show some type of message on front end
			System.out.println("Not enough inventory for this sale item");
		}
	}

	public void updateSale(Sale updatedSale) {
		Optional<Sale> sale = saleRepository.findById(updatedSale.getId());

		if (sale.isPresent()) {
			Sale existingSale = sale.get();
			
			// TODO: check if new quantity is within inventory count 
			
			// update item inventory depending on updated sale fields 
			if(existingSale.getItem() != updatedSale.getItem()) {
				Item existingItem = itemRepository.findById(existingSale.getItem().getId()).get();
				existingItem.setInventory(existingItem.getInventory() + existingSale.getQuantity());
				Item updatedItem = itemRepository.findById(updatedSale.getItem().getId()).get();
				updatedItem.setInventory(updatedItem.getInventory() - updatedSale.getQuantity());
			}
			else if(existingSale.getQuantity() != updatedSale.getQuantity()) {
				Item existingItem = itemRepository.findById(existingSale.getItem().getId()).get();
				int difference = updatedSale.getQuantity() - existingSale.getQuantity();
				int newCount = existingItem.getInventory() - difference;
				existingItem.setInventory(newCount);
			}

			existingSale.setDateOfSale(updatedSale.getDateOfSale());
			existingSale.setItem(updatedSale.getItem());
			existingSale.setCustomer(updatedSale.getCustomer());
			existingSale.setQuantity(updatedSale.getQuantity());
			existingSale.setSalePrice(updatedSale.getSalePrice());
			existingSale.setPaid(updatedSale.isPaid());
			saleRepository.save(updatedSale);
		}
	}

	public void deleteSaleById(Long saleId) {
		saleRepository.deleteById(saleId);
	}
	
	public List<Object[]> getTop3SaleItems() {
		return saleRepository.findTop3ByTotalQuantity();
	}
	
	public BigDecimal getTotalRevenue() {
		return saleRepository.getTotalRevenue();
	}
	
	public BigDecimal getTotalProfit() {
		return saleRepository.getTotalProfit();
	}
	
	public int getTotalSales() {
		return saleRepository.getTotalSales();
	}

}
