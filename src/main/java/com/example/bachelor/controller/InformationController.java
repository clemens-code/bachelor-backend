package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
