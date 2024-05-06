package com.mybankapp.mybank.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateAccountRequest{

	@Min(0)
	@NotNull(message = "Amount must not be null")
	private Double amount;
}
