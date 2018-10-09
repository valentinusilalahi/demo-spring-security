package com.silalahi.valentinus.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.silalahi.valentinus.app.config.LoginUser;
import com.silalahi.valentinus.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	final private MediaType contentTypeText = new MediaType(MediaType.TEXT_PLAIN.getType(),
			MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));
	
	final private MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(ctx)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	public void greeting() throws Exception{
        User user = new User(1L, "test_user", "pass", "aaa.aaa@example.com", true);
        LoginUser loginUser = new LoginUser(user);
        
        RequestBuilder builder = MockMvcRequestBuilders.get("/user")
        		.with(user(loginUser))
        		.accept(MediaType.TEXT_PLAIN);
        
        MvcResult result = mockMvc.perform(builder)
                .andExpect(authenticated().withUsername("test_user").withRoles("ADMIN", "USER"))
                .andExpect(content().contentType(contentTypeText))
                .andExpect(content().string("hello test_user"))
                //.andExpect(cookie().exists("XSRF-TOKEN"))
                .andExpect(forwardedUrl(null))
                .andExpect(redirectedUrl(null))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("hello test_user");
	}
	
	@WithMockUser(roles = "USER")
    @Test
    public void getEcho() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/user/echo/{message}", "abc")
                .accept(MediaType.TEXT_PLAIN);

        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeText))
                .andExpect(content().string("ABC"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("ABC");
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getEcho_403() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/user/echo/{message}", "abc");

        mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andDo(print());
    }

    @Test
    public void getEcho_401() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/user/echo/{message}", "abc");

        mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andDo(print());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void postEcho() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/user/echo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"message\": \"hello world\"}")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(content().string("{message=hello world}"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("{message=hello world}");
    }

}
