package com.mybankapp.mybank.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class AuthenticationService {


    private final CustomerService customerService;

    public AuthenticationService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean authenticate(String authorizationHeader){
        if(!isBasicAuthValid(authorizationHeader)){
            return false;
        }
        String username = extractUsername(authorizationHeader);
        String password = extractPassword(authorizationHeader);

        return customerService.authenticateCustomer(username, password);
    }

    private boolean isBasicAuthValid(String authorizationHeader){

            return authorizationHeader != null && authorizationHeader.startsWith("Basic ");
    }

    private String extractUsername(String authorizationHeader){
        if(!isBasicAuthValid(authorizationHeader)){
            return null;
        }
        String base64Credentials = authorizationHeader.substring(6);
        byte[] bytes = Base64.decodeBase64(base64Credentials);
        String credentials = new String(bytes, StandardCharsets.UTF_8);
        int colonIndex = credentials.indexOf(':');
        if(colonIndex == -1){
            return null;
        }

        return credentials.substring(0,colonIndex);
    }

    private String extractPassword(String authorizationHeader){
        if(!isBasicAuthValid(authorizationHeader)){
            return null;
        }
        String base64Credentials = authorizationHeader.substring(6);
        byte[] bytes = Base64.decodeBase64(base64Credentials);
        String credentials = new String(bytes, StandardCharsets.UTF_8);
        int colonIndex = credentials.indexOf(':');
        if(colonIndex == -1){
            return null;
        }

        return credentials.substring(colonIndex + 1);
    }
}
