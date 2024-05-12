package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gersimuca.Warehouse.Management.dto.request.AuthenticationRequest;
import com.gersimuca.Warehouse.Management.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testRegisterEndpoint() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setPassword("pass1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testLoginEndpoint() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("admin");
        request.setPassword("admin");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
