# Mybank Spring Boot Application
___

## Summary
___
This project provides to CRUD operations for customer and accounts that depends on existing customer.<br>

## Spring Security Basic Authentication Implementation:
- Role Enum class created for role base authorization
- AuthenticationService class created for authentication
- SecurityConfig class created, csrf configuration, AuthorizeHttpRequests were configured.
- In logging spring security debug mod, I got Invalid CSRF token found for ...../delete/{id} endpoint

When I checked the cookies on Postman, request header and response header has same JSessionID and XSRF-TOKEN
but security layer gave me the same error.

## Customer API Endpoints
___
1. GET "/v1/customer": is base endpoint and it returns List of CustomerDto
2. GET "/v1/customer/{id}": is returns specified CustomerDto, if Customer exist.
3. POST "/v1/customer/create": is required request that valid Customer, then request saves to DB and it returns CustomerDto.
4. PUT "/v1/customer/update/{id}": is update valid customer and it returns updated CustomerDto.
5. DELETE "/v1/customer/delete/{id}": is delete customer if exist, then returns Response Status 200 OK.

## Account API Endpoints
___
1. GET "/v1/account/": is base endpoint and it returns List of AccountDto
2. GET "/v1/account/{id}": is returns AccountDto, if Account is exist that searched id.
3. POST "/v1/account/create": is required valid Account request and exist customer id, then request saves to DB and it returns AccountDto.
4. DELETE "/v1/account/delete": is delete account if exist, then returns Response Status 200 OK.
5. PUT "/v1/account/deposit": is required exist account id and valid account request, then it returns updated AccountDto.
6. PUT "/v1/account/withdraw": is required exist account id and valid account request, then it returns updated AccountDto.

## Prerequisites
___
* Maven

## Tech Stack
___
* Java 17
* H2 Database
* Lombok
* Spring Boot
* Spring Data JPA
* Spring Security
