package com.perscholas.sims.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.perscholas.sims.model.Customer;
import com.perscholas.sims.model.Item;
import com.perscholas.sims.model.Sale;
import com.perscholas.sims.repository.ItemRepository;
import com.perscholas.sims.repository.SaleRepository;

@SpringBootTest
public class SaleServiceTest {

	@Autowired
	private SaleService saleService;
	
	@MockBean
	private SaleRepository saleRepository;
	
	@MockBean 
	private ItemRepository itemRepository;
	
	@Test
	public void getAllSalesTest() {
		Item item = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Customer customer = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Sale s1 = new Sale(100L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		Sale s2 = new Sale(200L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		Sale s3 = new Sale(300L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		when(saleRepository.findByOrderByDateOfSaleDesc()).thenReturn(Stream.of(s1, s2, s3).collect(Collectors.toList()));
		Assertions.assertEquals(3, saleService.getAllSales().size());
	}
	
	@Test
	public void getSalesByIdSuccessTest() {
		Item item = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Customer customer = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Sale s = new Sale(100L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		when(saleRepository.findById(100L)).thenReturn(Optional.of(s));
		Assertions.assertTrue(saleService.getSaleById(100L).isPresent());
	}
	
	@Test
	public void getSalesByIdFailTest() {
		when(saleRepository.findById(100L)).thenReturn(Optional.empty());
		Assertions.assertTrue(saleService.getSaleById(100L).isEmpty());
	}
	
	@Test
	public void addNewSaleTest() {
		Item item = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Customer customer = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Sale s = new Sale(100L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		when(itemRepository.findById(s.getItem().getId())).thenReturn(Optional.of(item));
		saleService.addNewSale(s);
		verify(itemRepository, times(1)).save(any());
		verify(saleRepository, times(1)).save(s);
	}
	
	@Test
	public void updateSaleTest() {
		Item item = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Customer customer = new Customer(100L, "Brett", "Parsons", "brett@gmail.com");
		Sale s = new Sale(100L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("33.33"), true);
		when(saleRepository.findById(100L)).thenReturn(Optional.of(s));
		Sale updated = new Sale(100L, Date.valueOf("2023-03-12"), item, customer, 10, new BigDecimal("77.77"), true);
		saleService.updateSale(updated);
		verify(saleRepository, times(1)).save(any());
	}
	
	@Test
	public void deleteSaleByIdTest() {
		saleService.deleteSaleById(100L);
		verify(saleRepository, times(1)).deleteById(100L);
	}
	
	@Test 
	public void getTotalRevenueTest() {
		when(saleRepository.getTotalRevenue()).thenReturn(new BigDecimal("10000"));
		Assertions.assertEquals(new BigDecimal("10000"), saleService.getTotalRevenue());
	}
	
	@Test
	public void getTotalProfitTest() {
		when(saleRepository.getTotalProfit()).thenReturn(new BigDecimal("5000"));
		Assertions.assertEquals(new BigDecimal("5000"), saleService.getTotalProfit());
	}
	
	@Test
	public void getTotalSalesTest() {
		when(saleRepository.getTotalSales()).thenReturn(1000);
		Assertions.assertEquals(1000, saleService.getTotalSales());
	}
}
