package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.service.impl.MetaDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/search")
public class InformationController {

    private static final Logger LOG = LoggerFactory.getLogger(InformationController.class);

    private MetaDataServiceImpl metaDataService;

    @CrossOrigin
    @GetMapping(value = "/all")
    public List<MetaData> allInfos(HttpServletRequest servletRequest)
    {
        return metaDataService.getAllMetaData(servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/group/{group}")
    public List<MetaData> infosByGroup(@PathVariable String group,  HttpServletRequest servletRequest){
        return metaDataService.getAllMetaDataByGroup(servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public Optional<MetaData> infoById(@PathVariable String id, HttpServletRequest servletRequest){
        return metaDataService.getMetaDataById(Long.parseLong(id), servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> infoWithImage(@PathVariable String id, HttpServletRequest servletRequest) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(metaDataService.getImageForId(Long.parseLong(id), servletRequest), headers, HttpStatus.OK);
        //return new ResponseEntity<>(metaDataService.getImage(Long.parseLong(id), servletRequest), headers, HttpStatus.OK);
    }


    @Resource
    public void setMetaDataService(MetaDataServiceImpl metaDataService){
        this.metaDataService=metaDataService;
    }

}
