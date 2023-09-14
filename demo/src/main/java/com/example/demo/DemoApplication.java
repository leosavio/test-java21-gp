package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class CustomersHttpController { 

	@PostMapping("/customers")
    Collection<Customer> customers(Customer customers) {
		//System.out.println(customers.getId());
        return Set.of(new Customer(1, "A"), new Customer(2, "B"), new Customer(3, "C"));
    }

    @GetMapping("/test")
    Collection<Customer> customersTest() {
        return Set.of(new Customer(1, "A"), new Customer(2, "B"), new Customer(3, "C"));
    }

    record Customer(Integer id, String name) {
    }

}
