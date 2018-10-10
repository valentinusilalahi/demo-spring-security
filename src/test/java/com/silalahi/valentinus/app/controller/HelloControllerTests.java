package com.silalahi.valentinus.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HelloController.class, secure = false)
public class HelloControllerTests {

	@Autowired
	private MockMvc mockMvc;

	final private MediaType contentTypeText = new MediaType(MediaType.TEXT_PLAIN.getType(),
			MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));

	@Test
	public void greeting() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_PLAIN);

		MvcResult result = mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeText))
				.andExpect(content().string("hello dunia"))
				.andDo(print()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello dunia");
	}

}
