package com.lukeshannon.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lukeshannon.model.Customer;

@Repository
public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long> {

	Customer findByName(String string);
	
}
