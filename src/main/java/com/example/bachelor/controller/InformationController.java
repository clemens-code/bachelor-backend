package com.example.bachelor.controller;


import com.example.bachelor.dto.metadata.ReturnMetaData;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/search")
public class InformationController {

    private static final Logger LOG = LoggerFactory.getLogger(InformationController.class);

    private MetaDataServiceImpl metaDataService;

    @GetMapping(value = "/all")
    public List<ReturnMetaData> allInfos(HttpServletRequest servletRequest){
        return metaDataService.getAllMetaData(servletRequest);
    }

    @GetMapping(value = "/group")
    public List<ReturnMetaData> infosByGroup(HttpServletRequest servletRequest){
        return metaDataService.getAllMetaDataByGroup(servletRequest);
    }

    @GetMapping(value = "/group/{group}")
    public List<ReturnMetaData> infosByGroupInPath(@PathVariable String group, HttpServletRequest servletRequest){
        return metaDataService.getAllMetaDataByGroup(group, servletRequest);
    }

    @GetMapping(value = "/id/{id}")
    public Optional<ReturnMetaData> infoById(@PathVariable String id, HttpServletRequest servletRequest){
        return metaDataService.getMetaDataById(Long.parseLong(id), servletRequest);
    }

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
