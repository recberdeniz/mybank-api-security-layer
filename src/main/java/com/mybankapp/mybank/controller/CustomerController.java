package com.mybankapp.mybank.controller;

import java.security.Principal;
import java.util.List;

import com.mybankapp.mybank.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.service.CustomerService;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;
    }


	@GetMapping("/myInfo")
	public ResponseEntity<CustomerDto> getCustomer(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		return ResponseEntity.ok(customerService.findCustomerWithUsername(username));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody UpdateCustomerRequest customerRequest,
			@PathVariable String id){
		return ResponseEntity.ok(customerService.updateCustomer(customerRequest, id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
		System.out.println("delete controller start working!");
		customerService.deleteCustomer(id);
		System.out.println("customer object were created!");
		return ResponseEntity.ok().build();
	}
	
	
	
}
