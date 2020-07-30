package com.example.bachelor.data;

import com.example.bachelor.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataUpload {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void testDataUploadWithValidRequest() throws Exception {
        String token = "Bearer "+jwtUtil.generateToken("Clemens");
        MockMultipartFile image = new MockMultipartFile("image", new ByteArrayInputStream(Files.readAllBytes(Paths.get("C:\\Users\\Clemens_2\\Pictures\\IMG_3074.jpg"))));
        MockMultipartFile json = new MockMultipartFile("metaDataInformation", "", "application/json", "{\"test\":\"daten\",\"test\":[\"daten\", \"fuer test\"]}".getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/data/create/")
                            .file(image)
                            .file(json)
                            .header("Authorization", token))
                            .andExpect(status().isOk());
    }
}
