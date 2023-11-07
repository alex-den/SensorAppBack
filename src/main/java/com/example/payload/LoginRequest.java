package com.example.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;

}
