package com.example.payload;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class UserInfoResponse {

	@NonNull
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private List<String> roles;

}
