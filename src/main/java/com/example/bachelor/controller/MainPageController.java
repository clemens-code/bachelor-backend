package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MainPageController {

    private MetadataRepository metadataRepository;

    @CrossOrigin
    @GetMapping(value = "/infos")
    public List<MetaData> infos()
    {
        return metadataRepository.findAll();
    }

    @Resource
    public void setMetadataRepository(MetadataRepository metadataRepository){this.metadataRepository=metadataRepository;}

}
