package com.mybankapp.mybank.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {

	@NotNull(message = "Currency must not be null")
	String currency;
}
