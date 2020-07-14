package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.service.impl.MetaDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value = "/data/create")
public class DataReceiveController {

    private static final Logger LOG = LoggerFactory.getLogger(DataReceiveController.class);

    private MetaDataServiceImpl metadataSaveService;

    @PutMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> receiveData(@RequestPart Object metaDataInformation,
                              @RequestPart(value = "image") final MultipartFile image,
                              @PathVariable String id,
                              HttpServletRequest servletRequest){
        Objects.requireNonNull(metaDataInformation, "the metadata information is not allowed to be null");
        LOG.info("PUT Request for saving Data.");
        if(metadataSaveService.saveMetaData(metaDataInformation, Long.parseLong(id), image, servletRequest) != null) {
            return ResponseEntity.ok().body("Request was processed successfully!");
        }else{
            return ResponseEntity.status(400).body("Server failed to process this request. Try later again!");
        }


    }

    @PostMapping(value = "/")
    public ResponseEntity<String> create(@RequestPart Object metaDataInformation,
                                           @RequestPart(value = "image", required = true) final MultipartFile image,
                                           HttpServletRequest servletRequest){
        Objects.requireNonNull(metaDataInformation, "The information for the Image is not allowed to be null!");
        LOG.info("POST Request for saving Data.");
        MetaData createdMetaData = metadataSaveService.saveMetaData(metaDataInformation, image, servletRequest);
        if(createdMetaData != null){
            return ResponseEntity.ok().body("Request was processed successfully! The ID for the Data is "+createdMetaData._id);
        }else {
            return ResponseEntity.status(400).body("Server failed to process this request. Try later again!");
        }
    }

    @Resource
    public void setMetadataSaveService(MetaDataServiceImpl metadataSaveService){
        this.metadataSaveService=metadataSaveService;
    }

}
