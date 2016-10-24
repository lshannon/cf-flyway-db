	package com.lukeshannon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CfFlywayDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfFlywayDbApplication.class, args);
	}
}

@RestController
class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "Welcome. Endpoints are /v1/add/{name} and /v1/customers/{name";
	}
}
