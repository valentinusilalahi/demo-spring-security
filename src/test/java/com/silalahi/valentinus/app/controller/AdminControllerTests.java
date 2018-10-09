package com.silalahi.valentinus.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silalahi.valentinus.app.config.LoginUser;
import com.silalahi.valentinus.app.config.SecurityConfig;
import com.silalahi.valentinus.app.entity.User;
import com.silalahi.valentinus.app.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminController.class)
@Import(value = { SecurityConfig.class })
public class AdminControllerTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;
	@MockBean(name = "simpleUserDetailsService")
	private UserDetailsService userDetailsService;

	final private MediaType contentTypeText = new MediaType(MediaType.TEXT_PLAIN.getType(),
			MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));

	@Test
	public void greeting() throws Exception {
		User user = new User(1L, "valentinus", "P455", "valentinus.silalahi@gmail.com", true);
		LoginUser loginUser = new LoginUser(user);
		RequestBuilder builder = MockMvcRequestBuilders.get("/admin").with(user(loginUser))
				.accept(MediaType.TEXT_PLAIN);

		MvcResult result = mvc.perform(builder).andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeText)).andExpect(content().string("hello admin valentinus"))
				.andDo(print()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("hello admin valentinus");
	}

	@Test
	public void greetingWithName() throws Exception {
		Mockito.when(userService.findByName(anyString()))
				.thenReturn(Optional.of(new User(2L, "sinabutar", "admin1!", "sinabutar@yahoo.com", false)));

		User user = new User(1L, "sinabutar", "admin1!", "sinabutar@yahoo.com", true);
		LoginUser loginUser = new LoginUser(user);
		RequestBuilder builder = MockMvcRequestBuilders.get("/admin/{name}", "sinabutar").with(user(loginUser))
				.accept(MediaType.TEXT_PLAIN);

		MvcResult result = mvc.perform(builder).andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeText)).andExpect(content().string("hello sinabutar"))
				.andDo(print()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("hello sinabutar");
	}

}
