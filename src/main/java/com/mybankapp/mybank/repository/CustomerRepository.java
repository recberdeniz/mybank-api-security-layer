package com.mybankapp.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mybankapp.mybank.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String>{

    Optional<Customer> findByUsername(String username);
}
