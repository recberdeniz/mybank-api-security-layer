package com.mybankapp.mybank.controller;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.service.AuthenticationService;
import com.mybankapp.mybank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/public")
public class PublicController {

    private final AuthenticationService authenticationService;
    private final CustomerService customerService;


    public PublicController(AuthenticationService authenticationService, CustomerService customerService) {
        this.authenticationService = authenticationService;
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<String> greetings(){
        return ResponseEntity.ok("Welcome to mybank application!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authorizationHeader){

        if(authenticationService.authenticate(authorizationHeader)){
            return ResponseEntity.ok("Login Successfully!");
        } else{
            return ResponseEntity.ok("Access Denied!");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }
}
