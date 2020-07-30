package com.example.bachelor.controller;

import com.example.bachelor.service.impl.MetaDataServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/data/manipulate")
public class DataManipulateController {

    private MetaDataServiceImpl metaDataService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDataForID(@PathVariable String id, HttpServletRequest servletRequest){
       try{
           metaDataService.deleteEntityByID(Long.parseLong(id), servletRequest);
           return ResponseEntity.ok().body("The entity was deleted successful");
       }catch (IllegalArgumentException | UnsupportedOperationException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }catch (UnknownError e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEntityByID(@RequestPart(required = false) Object metaDataInformation,
                                                   @RequestPart(value = "image", required = false) final MultipartFile image,
                                                   @PathVariable String id,
                                                   HttpServletRequest servletRequest){
        try{
            metaDataService.updateMetaData(metaDataInformation, Long.parseLong(id), image, servletRequest);
        }catch (IllegalArgumentException | UnsupportedOperationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Update successful!");
    }


    @Resource
    public void setMetaDataService(MetaDataServiceImpl metaDataService){
        this.metaDataService=metaDataService;
    }
}
