package com.mybankapp.mybank.controller;

import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.service.AccountService;
import com.mybankapp.mybank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    private final CustomerService customerService;
    private final AccountService accountService;

    public AdminController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    // Find Customer By id
    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDto> getCustomerWithId(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomerWithId(id));
    }

    // Find All Customers
    @GetMapping("/customer/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/account/getAll")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getAccountWithId(@PathVariable String id){
        return ResponseEntity.ok(accountService.getAccountWithId(id));
    }

}
