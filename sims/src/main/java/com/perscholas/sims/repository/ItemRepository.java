package com.perscholas.sims.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
//	List<Item> findByUserId(Long userId);
	Optional<Item> findByName(String name);
	
}
