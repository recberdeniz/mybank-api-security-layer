package com.mybankapp.mybank;

import java.util.ArrayList;
import java.util.Set;

import com.mybankapp.mybank.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MybankApplication implements CommandLineRunner{

	private final CustomerRepository customerRepository;
	private final BCryptPasswordEncoder encoder;
	
	public MybankApplication(CustomerRepository customerRepository, BCryptPasswordEncoder encoder) {

		this.customerRepository = customerRepository;
        this.encoder = encoder;
    }
	public static void main(String[] args) {
		SpringApplication.run(MybankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer c1 = customerRepository
				.save(new Customer(null,
						"john",
						encoder.encode("1234"),
						"John",
						1989,
						City.ANKARA,
						"Ev",
						new ArrayList<>(),
						Set.of(Role.ROLE_USER),
						true,
						true,
						true,
						true));
		Customer c2 = customerRepository
				.save(new Customer(null,
						"ginger",
						encoder.encode("1234"),
						"Ginger",
						1995,
						City.ISTANBUL,
						"Ev",
						new ArrayList<>(),
						Set.of(Role.ROLE_USER),
						true,
						true,
						true,
						true));
		Customer c3 = customerRepository
				.save(new Customer(null,
						"susan",
						encoder.encode("1234"),
						"Susan",
						1999,
						City.IZMIR,
						"Ev",
						new ArrayList<>(),
						Set.of(Role.ROLE_ADMIN),
						true,
						true,
						true,
						true));
		System.out.println(c1);
		
	}

}
