package com.perscholas.sims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByItemId(Long itemId);

	List<Sale> findByCustomerId(Long custId);

	void deleteByItemId(Long itemId);

	void deleteByCustomerId(Long custId);
	
	List<Sale> findByOrderByDateOfSaleDesc();

	@Query(
		value = "SELECT item_id, SUM(quantity), SUM(sale_price*quantity) FROM sale GROUP BY item_id ORDER BY SUM(quantity) DESC LIMIT 3",
		nativeQuery = true)
	List<Object[]> findTop3ByTotalQuantity();
}
