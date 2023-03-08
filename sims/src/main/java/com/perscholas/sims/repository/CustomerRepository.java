package com.perscholas.sims.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findByOrderByFirstName();

	Optional<Customer> findByEmail(String email);
}
