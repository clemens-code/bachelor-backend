package com.example.bachelor.controller;

import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void testAllInfos() throws Exception {
        String token = "Bearer "+jwtUtil.generateToken("Clemens");
        mockMvc.perform(get("/search/all").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(metadataRepository.findByOwner("Clemens"))));
    }
}
