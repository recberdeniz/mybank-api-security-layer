package com.mybankapp.mybank.service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.mybankapp.mybank.exception.UsernameAlreadyExistsException;
import com.mybankapp.mybank.model.Role;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.converter.CustomerDtoConverter;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.exception.InvalidCityException;
import com.mybankapp.mybank.exception.CustomerNotFoundException;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

	private final CustomerRepository customerRepository;
	private final CustomerDtoConverter converter;
	private final BCryptPasswordEncoder passwordEncoder;
	
	
	public CustomerService(CustomerRepository customerRepository,
                           CustomerDtoConverter converter, BCryptPasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }


	public CustomerDto getCustomerWithId(String id) {
		
		return converter.
				convert(
						findCustomerById(id)
						);
	}


	public List<CustomerDto> getAllCustomers() {
		
		return customerRepository.
				findAll().
				stream().
				map(converter::convert).collect(Collectors.toList());
	}


	public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
		Optional<Customer> existingCustomer = customerRepository.findByUsername(customerRequest.getUsername());
		if(existingCustomer.isPresent()){
			throw new UsernameAlreadyExistsException("Username already exists!: " + customerRequest.getUsername());
		}
		City city = checkCityList(customerRequest.getCity());
		Customer customer = customerRepository.save(new Customer(null,
				customerRequest.getUsername(),
				passwordEncoder.encode(customerRequest.getPassword()),
				customerRequest.getName(),
				customerRequest.getDateOfBirth(),
				city,
				customerRequest.getAddress(),
				new ArrayList<>(),
				Set.of(Role.ROLE_USER),
				true,
				true,
				true,
				true));
		return converter.convert(customer);
	}
	
	private static City checkCityList(String city) {
		return Arrays.stream(City.values())
				.filter(c -> c.name().equals(city))
				.findFirst()
				.orElseThrow(() ->
				new InvalidCityException("Invalid city name: " + city));
	}


	public CustomerDto updateCustomer(UpdateCustomerRequest customerRequest, String id) {
		Customer customer = findCustomerById(id);
		
		City city = checkCityList(customerRequest.getCity());
		customer.setCity(city);
		customer.setAddress(customerRequest.getAddress());
		Customer updatedCustomer = customerRepository.save(customer);
		return converter.convert(updatedCustomer);
	}
	
	protected Customer findCustomerById(String id) {
		return customerRepository
				.findById(id)
				.orElseThrow(
						() -> new CustomerNotFoundException("Customer could not find with id: " + id)
						);
	}


	public void deleteCustomer(String id) {
		
		Customer customer = findCustomerById(id);
		customerRepository.delete(customer);
	}

	public CustomerDto findCustomerWithUsername(String username){
		Customer customer = (Customer) loadUserByUsername(username);
		return converter.convert(customer);
	}

	protected boolean authenticateCustomer(String username, String password){
		Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
		if(optionalCustomer.isEmpty()){
			return false;
		}
		Customer customer = optionalCustomer.get();
        return passwordEncoder.matches(password, customer.getPassword());
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> customer = customerRepository.findByUsername(username);
		return customer.orElseThrow(EntityNotFoundException::new);
	}

	
	

}
