package com.perscholas.sims.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.dto.ProfitByMonth;
import com.perscholas.sims.dto.RevenueByMonth;
import com.perscholas.sims.dto.SalesByMonth;
import com.perscholas.sims.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByItemId(Long itemId);

	List<Sale> findByCustomerId(Long custId);

	void deleteByItemId(Long itemId);

	void deleteByCustomerId(Long custId);
	
	List<Sale> findByOrderByDateOfSaleDesc();
	
	@Query(
		value = "SELECT SUM(sale_price*quantity) FROM sale",
		nativeQuery = true)
	BigDecimal getTotalRevenue();
	
	@Query(
		value = "SELECT (SUM(s.quantity*s.sale_price) - SUM(s.quantity*i.cost)) FROM sale s, item i WHERE s.item_id = i.id",
		nativeQuery = true)
	BigDecimal getTotalProfit();
	
	@Query(
		value = "SELECT SUM(quantity) FROM sale",
		nativeQuery = true)
	int getTotalSales();

	@Query(
		value = "SELECT item_id, SUM(quantity), SUM(sale_price*quantity) FROM sale GROUP BY item_id ORDER BY SUM(quantity) DESC LIMIT 3",
		nativeQuery = true)
	List<Object[]> findTop3ByTotalQuantity();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', SUM(quantity) as 'salesCount' FROM sale GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<SalesByMonth> getSalesCountByMonth();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', (SUM(s.quantity*s.sale_price) - SUM(s.quantity*i.cost)) as 'profit' FROM sale s, item i WHERE s.item_id = i.id GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<ProfitByMonth> getProfitByMonth();
	
	@Query(
		value = "SELECT MONTH(date_of_sale) as 'month', SUM(s.quantity*s.sale_price) as 'revenue' FROM sale s, item i WHERE s.item_id = i.id GROUP BY MONTH(date_of_sale) ORDER BY MONTH(date_of_sale)",
		nativeQuery = true)
	List<RevenueByMonth> getRevenueByMonth();
	
}
