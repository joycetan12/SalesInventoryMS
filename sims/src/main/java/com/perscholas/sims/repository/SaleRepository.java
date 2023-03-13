package com.perscholas.sims.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.dto.ProfitByMonth;
import com.perscholas.sims.dto.RevenueByMonth;
import com.perscholas.sims.dto.SalesByMonth;
import com.perscholas.sims.dto.TopSaleItems;
import com.perscholas.sims.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByItemId(Long itemId);

	List<Sale> findByCustomerId(Long custId);

	void deleteByItemId(Long itemId);

	void deleteByCustomerId(Long custId);
	
	List<Sale> findByOrderByDateOfSaleDesc();
	
	@Query(
		value = "SELECT SUM(sale_price*quantity) FROM sale WHERE paid = 1",
		nativeQuery = true)
	BigDecimal getTotalRevenue();
	
	@Query(
		value = "SELECT (SUM(s.quantity*s.sale_price) - SUM(s.quantity*i.cost)) FROM sale s, item i WHERE s.item_id = i.id AND s.paid = 1",
		nativeQuery = true)
	BigDecimal getTotalProfit();
	
	@Query(
		value = "SELECT SUM(quantity) FROM sale WHERE paid = 1",
		nativeQuery = true)
	int getTotalSales();

	@Query(
		value = "SELECT i.name as 'itemName', SUM(s.quantity) as 'quantity', SUM(s.sale_price*s.quantity) as 'revenue' FROM sale s, item i WHERE s.item_id = i.id AND paid = 1 GROUP BY i.name ORDER BY SUM(s.quantity) DESC LIMIT 3",
		nativeQuery = true)
	List<TopSaleItems> findTop3ByTotalQuantity();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', SUM(quantity) as 'salesCount' FROM sale WHERE paid = 1 GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<SalesByMonth> getSalesCountByMonth();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', (SUM(s.quantity*s.sale_price) - SUM(s.quantity*i.cost)) as 'profit' FROM sale s, item i WHERE s.item_id = i.id AND s.paid = 1 GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<ProfitByMonth> getProfitByMonth();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', SUM(s.quantity*s.sale_price) as 'revenue' FROM sale s, item i WHERE s.item_id = i.id AND s.paid = 1 GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<RevenueByMonth> getRevenueByMonth();
	
}
