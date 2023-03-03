package com.perscholas.sims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perscholas.sims.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
