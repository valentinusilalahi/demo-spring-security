package com.silalahi.valentinus.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silalahi.valentinus.app.config.LoginUser;
import com.silalahi.valentinus.app.entity.Memo;
import com.silalahi.valentinus.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoControllerIntegrationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	final private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void getMemo() throws Exception {
		Memo expected = new Memo(1L, "memo shopping", "memo1 description", false,
				LocalDateTime.of(2018, 1, 4, 12, 1, 0));
		String expectedJson = objectMapper.writeValueAsString(expected);

		User user = new User(1L, "aaaa", "pass", "aaa.aaa@example.com", true);
		LoginUser loginUser = new LoginUser(user);

		RequestBuilder builder = MockMvcRequestBuilders.get("/memo/{id}", expected.getId()).with(user(loginUser))
				.with(csrf()).accept(MediaType.APPLICATION_JSON_UTF8);

		MvcResult result = mockMvc.perform(builder).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.title").value(expected.getTitle()))
				.andExpect(jsonPath("$.description").value(expected.getDescription()))
				.andExpect(jsonPath("$.done").value(expected.getDone())).andDo(print()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
	}

}
