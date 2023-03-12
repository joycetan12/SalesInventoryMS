package com.perscholas.sims.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.perscholas.sims.model.Item;
import com.perscholas.sims.repository.ItemRepository;
import com.perscholas.sims.repository.SaleRepository;

@SpringBootTest
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;
	
	@MockBean
	private ItemRepository itemRepository;
	
	@MockBean
	private SaleRepository saleRepository;
	
	@Test
	public void getAllItemsTest() {
		Item i1 = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Item i2 = new Item(200L, "item2", "desc2", 20, new BigDecimal("19.99"));
		Item i3 = new Item(300L, "item3", "desc3", 30, new BigDecimal("19.99"));
		when(itemRepository.findByOrderByName()).thenReturn(Stream.of(i1, i2, i3).collect(Collectors.toList()));
		Assertions.assertEquals(3, itemService.getAllItems().size());
	}
	
	@Test
	public void getItemByIdSuccessTest() {
		Item i = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		when(itemRepository.findById(100L)).thenReturn(Optional.of(i));
		Assertions.assertTrue(itemService.getItemById(100L).isPresent());
	}
	
	@Test
	public void getItemByIdFailTest() {
		when(itemRepository.findById(100L)).thenReturn(Optional.empty());
		Assertions.assertTrue(itemService.getItemById(100L).isEmpty());
	}
	
	@Test
	public void getAllLowInventoryTest() {
		Item i1 = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		Item i2 = new Item(200L, "item2", "desc2", 20, new BigDecimal("19.99"));
		when(itemRepository.findByInventoryLessThan(30)).thenReturn(Stream.of(i1, i2).collect(Collectors.toList()));
		Assertions.assertEquals(2, itemService.getAllLowInventory(30).size());
	}
	
	@Test
	public void getItemByNameSuccessTest() {
		Item i = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		when(itemRepository.findByName("item1")).thenReturn(Optional.of(i));
		Assertions.assertTrue(itemService.getItemByName("item1").isPresent());
	}
	
	@Test
	public void getItemByNameFailTest() {
		when(itemRepository.findByName("item1")).thenReturn(Optional.empty());
		Assertions.assertTrue(itemService.getItemByName("item1").isEmpty());
	}
	
	@Test
	public void addNewItemTest() {
		Item i = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		itemService.addNewItem(i);
		verify(itemRepository, times(1)).save(i);
	}
	
	@Test
	public void updateItemTest() {
		Item i = new Item(100L, "item1", "desc1", 10, new BigDecimal("19.99"));
		when(itemRepository.findById(100L)).thenReturn(Optional.of(i));
		Item updated = new Item(100L, "item1", "desc1", 10, new BigDecimal("99.99"));
		itemService.updateItem(updated);
		verify(itemRepository, times(1)).save(any());
	}
	
	@Test
	public void deleteItemByIdTest() {
		itemService.deleteItemById(100L);
		verify(saleRepository, times(1)).deleteByItemId(100L);
		verify(itemRepository, times(1)).deleteById(100L);
	}
}
