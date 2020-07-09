package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.service.impl.MetaDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/search")
public class InformationController {

    private MetaDataServiceImpl metaDataService;

    @CrossOrigin
    @GetMapping(value = "/all")
    public List<MetaData> allInfos(HttpServletRequest servletRequest)
    {
        return metaDataService.getAllMetaData(servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/group")
    public List<MetaData> infosByGroup(HttpServletRequest servletRequest){
        return metaDataService.getAllMetaDataByGroup(servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public Optional<MetaData> infoById(@RequestParam String _id){
        return metaDataService.getMetaDataById(Long.parseLong(_id));
    }
    @Resource
    public void setMetaDataService(MetaDataServiceImpl metaDataService){
        this.metaDataService=metaDataService;
    }

}
