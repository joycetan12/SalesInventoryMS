package com.perscholas.sims.service;

import java.util.List;
import java.util.Optional;

import com.perscholas.sims.model.Item;

public interface ItemService {

	List<Item> getAllItems();
	Optional<Item> getItemById(Long itemId);
	List<Item> getAllLowInventory(int quantity);
	Optional<Item> getItemByName(String itemName);
	void addNewItem(Item item);
	void updateItem(Item updatedItem);
	void deleteItemById(Long itemId);
	
}
