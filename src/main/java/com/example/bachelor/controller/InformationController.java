package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.entities.user.User;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.repository.user.UserRepository;
import com.example.bachelor.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InformationController {



    @Autowired
    private MetadataRepository metadataRepository;


    @CrossOrigin
    @GetMapping(value = "/infos")
    public List<MetaData> infos()
    {
        return  metadataRepository.findAll();
    }


}
