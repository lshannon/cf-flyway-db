package com.lukeshannon;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukeshannon.model.Customer;
import com.lukeshannon.repo.CustomerRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CfFlywayDbApplicationTests {

	private static final String NAME = "Biff";

	@Autowired
	private CustomerRepo customerRepo;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testWrite() {
		Customer customer = new Customer();
		customer.setName(NAME);
		assertNotNull(customerRepo.save(customer));
	}

	@Test
	public void testRead() {
		Customer customer = new Customer();
		customer.setName(NAME);
		assertNotNull(customerRepo.findByName(NAME));
	}

}
