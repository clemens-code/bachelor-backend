package com.example.bachelor.controller;

import com.example.bachelor.entities.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    public void WrongUserAuthTest() throws Exception {
        mockMvc.perform(post("/authenticate").header("Origin", "*").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(new User("TestTestNutzer", "Nutzer", "paswortTest"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Username or Password!"));
    }

    @Test
    public void correctUserTest() throws Exception{
        mockMvc.perform(post("/authenticate").header("Origin", "*").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(new User("Clemens", "admin", "test"))))
                .andExpect(status().isOk());
    }
}
