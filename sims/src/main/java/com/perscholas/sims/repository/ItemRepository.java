package com.perscholas.sims.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	Optional<Item> findByName(String name);
	
	List<Item> findByInventoryLessThan(int inventory);
	
	List<Item> findByOrderByName();
}
