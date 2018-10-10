package com.silalahi.valentinus.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

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
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIntegrationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	final private MediaType contentTypeText = new MediaType(MediaType.TEXT_PLAIN.getType(),
			MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));

	@Test
	public void postGreeting() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.post("/hello").with(csrf()).param("message", "WORLD")
				.accept(MediaType.TEXT_PLAIN);

		MvcResult result = mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeText))
				.andExpect(content().string("hello world"))
				.andDo(print()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("hello WORLD");
	}

}
