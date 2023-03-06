package com.perscholas.sims.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Sale;

import jakarta.transaction.Transactional;

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
		value = "SELECT COUNT(*) FROM sale",
		nativeQuery = true)
	int getTotalSales();

	@Query(
		value = "SELECT item_id, SUM(quantity), SUM(sale_price*quantity) FROM sale GROUP BY item_id ORDER BY SUM(quantity) DESC LIMIT 3",
		nativeQuery = true)
	List<Object[]> findTop3ByTotalQuantity();
}
