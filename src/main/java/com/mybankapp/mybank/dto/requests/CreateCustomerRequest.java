package com.mybankapp.mybank.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest extends BaseCustomerRequest{

	@NotBlank(message = "Name must not be null!")
	private String name;
	@NotNull(message = "Date of birth must not be null!")
	private Integer dateOfBirth;

}
