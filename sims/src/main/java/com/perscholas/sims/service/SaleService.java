package com.perscholas.sims.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.perscholas.sims.model.Sale;

public interface SaleService {

	List<Sale> getAllSales();
	Optional<Sale> getSaleById(Long saleId);
	void addNewSale(Sale sale);
	void updateSale(Sale updatedSale);
	void deleteSaleById(Long saleId);
	List<Object[]> getTop3SaleItems();
	BigDecimal getTotalRevenue();
	BigDecimal getTotalProfit();
	int getTotalSales();
	Map<String, Integer> getSalesCountByMonth();
	Map<String, BigDecimal> getProfitByMonth();
	Map<String, BigDecimal> getRevenueByMonth();
	
}
