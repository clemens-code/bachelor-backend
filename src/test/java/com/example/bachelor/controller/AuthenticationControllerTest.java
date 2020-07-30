package com.example.bachelor.controller;

import com.example.bachelor.entities.user.User;
import com.example.bachelor.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void WrongUserAuthTest() throws Exception {
        mockMvc.perform(post("/authenticate")
                .header("Access-Control-Allow-Methods", "POST")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"testnutzer\",\n" +
                        "    \"password\": \"falschesPasswort\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Username or Password!"));
    }

    @Test
    public void correctUserTest() throws Exception{
        MvcResult res = mockMvc.perform(post("/authenticate")
                .header("Access-Control-Allow-Methods", "POST")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"username\": \"Clemens\",\n" +
                        "    \"password\": \"test\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
        String responseToken = res.getResponse().getContentAsString();

        assertEquals("Clemens", jwtUtil.extractUsername(responseToken));
    }
}
