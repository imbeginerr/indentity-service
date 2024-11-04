package com.hygge.identityservice.service;

import com.hygge.identityservice.dto.request.UserCreationRequest;
import com.hygge.identityservice.dto.response.UserResponse;
import com.hygge.identityservice.entity.User;
import com.hygge.identityservice.exception.AppException;
import com.hygge.identityservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private User user;
	private UserCreationRequest request;
	private UserResponse response;
	private LocalDate dob;

	@BeforeEach
	void initData() {
		//GIVEN
		dob = LocalDate.of(1990, 1, 1);
		request = UserCreationRequest.builder().username("Thomas Shelby").firstName("Thomas").lastName("Shelby").password("12345678").dob(
				dob).build();

		response = UserResponse.builder().id("qwertygde3454").username("Thomas Shelby").firstName("Thomas").lastName("Shelby").dob(dob).build();

		user = User.builder().id("qwertygde3454").username("Thomas Shelby").firstName("Thomas").lastName("Shelby").dob(dob).build();
	}

	@Test
	void createUser_validRequest_success() {
		//GIVEN
		when(userRepository.existsByUsername(anyString())).thenReturn(false);
		when(userRepository.save(any())).thenReturn(user);

		//WHEN
		var response = userService.createUser(request);

		//THEN
		assertEquals("qwertygde3454", response.getId());
		assertEquals("Thomas Shelby", response.getUsername());

	}

	@Test
	void createUser_userExisted_fail() {
		//GIVEN
		when(userRepository.existsByUsername(anyString())).thenReturn(true);

		//WHEN
		var exception = assertThrows(AppException.class, () -> userService.createUser(request));

		//THEN
		assertEquals(exception.getErrorCode().getCode(), 1002);
	}
}
