package com.bankofaffiliates.authservice;

import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SecureOAuth2ApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChain;

    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.context).addFilters(this.filterChain).build();
        SecurityContextHolder.clearContext();
    }

    @Test
    public void permitIndex() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void usePassword() throws Exception {
        String header = "Basic " + new String(Base64.getEncoder().encode("browser:test".getBytes()));
        MvcResult result = this.mvc
                .perform(post("/oauth/token").header("Authorization", header)
                        .param("grant_type", "password")
                        .param("scope", "ui")
                        .param("username", "user1")
                        .param("password", "password1"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
        Object accessToken = this.objectMapper
                .readValue(result.getResponse().getContentAsString(), Map.class)
                .get("access_token");

        MvcResult getMe = this.mvc
                .perform(get("/users/current").accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(header().string("Content-Type",
                        MediaType.APPLICATION_JSON.toString() + ";charset=UTF-8"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void useClientCredentials() throws Exception {
        String header = "Basic " + new String(Base64.getEncoder().encode("user-service:test".getBytes()));
        MvcResult result = this.mvc
                .perform(post("/oauth/token").header("Authorization", header)
                        .param("grant_type", "client_credentials")
                        .param("scope", "server"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
        Object accessToken = this.objectMapper
                .readValue(result.getResponse().getContentAsString(), Map.class)
                .get("access_token");

        MvcResult getMe = this.mvc
                .perform(get("/me").accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(header().string("Content-Type",
                        MediaType.APPLICATION_JSON.toString() + ";charset=UTF-8"))
                .andExpect(status().isOk()).andDo(print()).andReturn();
    }

}
