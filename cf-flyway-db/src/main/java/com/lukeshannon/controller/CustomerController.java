/** 
 * 
 */
package com.lukeshannon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lukeshannon.model.Customer;
import com.lukeshannon.repo.CustomerRepo;

/**
 * @author luke
 *
 */
@RestController
@RequestMapping("/v1")
public class CustomerController {
	
	@Autowired
	private CustomerRepo customerRepo;

	@RequestMapping("/customer/{name}")
	public ResponseEntity<Customer> customer(@PathVariable String name) {
		Customer customer = customerRepo.findByName(name);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/add/{name}")
	public void add(@PathVariable String name) {
		Customer customer = new Customer();
		customer.setName(name);
		customerRepo.save(customer);
	}
}
