package com.mybankapp.mybank.dto.requests;

import com.mybankapp.mybank.model.City;

import com.mybankapp.mybank.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class BaseCustomerRequest {

	@NotNull(message = "Username must not be null")
	private String username;
	@NotNull(message = "Password must not be null")
	private String password;
	@NotNull(message = "City must not be null")
	private String city;
	@NotNull(message = "Address must not be null")
	private String address;

	private Set<Role> authorities;

	private boolean isEnabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
}
