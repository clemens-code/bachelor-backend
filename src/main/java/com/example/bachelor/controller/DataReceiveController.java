package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.service.impl.MetadataSaveServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.awt.*;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value = "/data/create")
public class DataReceiveController {

    private MetadataSaveServiceImpl metadataSaveService;

    @PutMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    public String receiveData(@RequestPart(required = true) Object metaDataInformation,
                              @RequestPart(value = "image", required = true) final MultipartFile image,
                              @PathVariable String id){
        Objects.requireNonNull(metaDataInformation, "the metadata information is not allowed to be null");
        metadataSaveService.saveMetaData(metaDataInformation, Long.parseLong(id), image);
        return null;
    }

    @PostMapping(value = "/")
    public ResponseEntity<MetaData> create(@RequestPart(required = true) Object metaDataInformation,
                                           @RequestPart(value = "image", required = true) final MultipartFile image){
        MetaData createdMetaData = metadataSaveService.saveMetaData(metaDataInformation, image);
        if(createdMetaData == null){
            return ResponseEntity.notFound().build();
        }else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdMetaData.get_id()).toUri();
            return ResponseEntity.created(uri).body(createdMetaData);
        }
    }

    @Resource
    public void setMetadataSaveService(MetadataSaveServiceImpl metadataSaveService){
        this.metadataSaveService=metadataSaveService;
    }

}
