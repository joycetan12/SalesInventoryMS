package com.perscholas.sims.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perscholas.sims.dto.ProfitByMonth;
import com.perscholas.sims.dto.RevenueByMonth;
import com.perscholas.sims.dto.SalesByMonth;
import com.perscholas.sims.model.Item;
import com.perscholas.sims.model.Sale;
import com.perscholas.sims.repository.ItemRepository;
import com.perscholas.sims.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

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

		// update item inventory based on sale quantity
		int newCount = existingItem.getInventory() - sale.getQuantity();
		existingItem.setInventory(newCount);
		itemRepository.save(existingItem);
		saleRepository.save(sale);
	}

	public void updateSale(Sale updatedSale) {
		Optional<Sale> sale = saleRepository.findById(updatedSale.getId());

		if (sale.isPresent()) {
			Sale existingSale = sale.get();

			// update item inventory depending on updated sale fields
			if (existingSale.getItem() != updatedSale.getItem()) {
				Item existingItem = itemRepository.findById(existingSale.getItem().getId()).get();
				existingItem.setInventory(existingItem.getInventory() + existingSale.getQuantity());
				Item updatedItem = itemRepository.findById(updatedSale.getItem().getId()).get();
				updatedItem.setInventory(updatedItem.getInventory() - updatedSale.getQuantity());
			} else if (existingSale.getQuantity() != updatedSale.getQuantity()) {
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

	public Map<String, Integer> getSalesCountByMonth() {
		List<SalesByMonth> salesCountByMonthList = saleRepository.getSalesCountByMonth();
		Map<Integer, Integer> salesMonthMap = new HashMap<>();
		for(SalesByMonth s: salesCountByMonthList) {
			salesMonthMap.put(s.getMonth(), s.getSalesCount());
		}
		
		Map<String, Integer> salesMap = new LinkedHashMap<>();
		for(int i = 1; i <= 12; i++) {
			if(salesMonthMap.containsKey(i)) {
				salesMap.put(getMonthFromNumber(i), salesMonthMap.get(i));
			}
			else {
				salesMap.put(getMonthFromNumber(i), 0);
			}
		}
		
		return salesMap;
	}
	
	public Map<String, BigDecimal> getProfitByMonth() {
		List<ProfitByMonth> profitByMonthList = saleRepository.getProfitByMonth();
		Map<Integer, BigDecimal> profitMonthMap = new HashMap<>();
		for(ProfitByMonth p: profitByMonthList) {
			profitMonthMap.put(p.getMonth(), p.getProfit());
		}
		
		Map<String, BigDecimal> profitMap = new LinkedHashMap<>();
		for(int i = 1; i <= 12; i++) {
			if(profitMonthMap.containsKey(i)) {
				profitMap.put(getMonthFromNumber(i), profitMonthMap.get(i));
			}
			else {
				profitMap.put(getMonthFromNumber(i), new BigDecimal("0"));
			}
		}
		
		return profitMap;
	}
	
	public Map<String, BigDecimal> getRevenueByMonth() {
		List<RevenueByMonth> revenueByMonthList = saleRepository.getRevenueByMonth();
		Map<Integer, BigDecimal> revenueMonthMap = new HashMap<>();
		for(RevenueByMonth r: revenueByMonthList) {
			revenueMonthMap.put(r.getMonth(), r.getRevenue());
		}
		
		Map<String, BigDecimal> revenueMap = new LinkedHashMap<>();
		for(int i = 1; i <= 12; i++) {
			if(revenueMonthMap.containsKey(i)) {
				revenueMap.put(getMonthFromNumber(i), revenueMonthMap.get(i));
			}
			else {
				revenueMap.put(getMonthFromNumber(i), new BigDecimal("0"));
			}
		}
		
		return revenueMap;
	}

	public String getMonthFromNumber(int monthNumber) {
		String month = "";
		switch(monthNumber) {
		case 1:
			month = "Jan";
			break;
		case 2:
			month = "Feb";
			break;
		case 3:
			month = "Mar";
			break;
		case 4:
			month = "Apr";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "Jun";
			break;
		case 7:
			month = "Jul";
			break;
		case 8:
			month = "Aug";
			break;
		case 9:
			month = "Sep";
			break;
		case 10:
			month = "Oct";
			break;
		case 11:
			month = "Nov";
			break;
		case 12:
			month = "Dec";
			break;
		}
		return month;
	}

}
