package com.hygge.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hygge.identityservice.dto.request.UserCreationRequest;
import com.hygge.identityservice.dto.response.UserResponse;
import com.hygge.identityservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private UserCreationRequest request;
	private UserResponse response;
	private LocalDate dob;

	@BeforeEach
	void initData() {
		//GIVEN
		dob = LocalDate.of(1990, 1, 1);
		request = UserCreationRequest.builder().username("Thomas Shelby").firstName("Thomas").lastName("Shelby").password("12345678").dob(dob).build();

		response = UserResponse.builder().id("qwertygde3454").username("Thomas Shelby").firstName("Thomas").lastName("Shelby").dob(dob).build();
	}

	@Test
	void createUser_validRequest_success() throws Exception {
		log.info("createUser");
		//GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(request);

		Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);

		//WHEN, THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(content)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)).andExpect(MockMvcResultMatchers.jsonPath("result.id").value("qwertygde3454"));

	}

//	@Test
//	void createUser_usernameInvalidRequest_fail() throws Exception {
//		log.info("createUser");
//		//GIVEN
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new JavaTimeModule());
//		String content = objectMapper.writeValueAsString(request);
//
//		Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);
//
//		//WHEN, THEN
//		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(content)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("code").value(1002)).andExpect(MockMvcResultMatchers.jsonPath("result.id").value("qwertygde3454"));
//
//	}
}
