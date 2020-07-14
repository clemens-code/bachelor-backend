package com.example.bachelor.controller;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.entities.response.ResponseDataWithImageObject;
import com.example.bachelor.entities.user.User;
import com.example.bachelor.service.impl.MetaDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @GetMapping(value = "/group")
    public List<MetaData> infosByGroup(HttpServletRequest servletRequest){
        return metaDataService.getAllMetaDataByGroup(servletRequest);
    }

    @CrossOrigin
    @GetMapping(value = "/id/{id}")
    public Optional<MetaData> infoById(@PathVariable String id){
        return metaDataService.getMetaDataById(Long.parseLong(id));
    }

    @CrossOrigin
    @GetMapping(value = "/image/{id}")
    @ResponseBody
    public ResponseEntity<ResponseDataWithImageObject> infoWithImage(@PathVariable String id) {
        ResponseDataWithImageObject data = metaDataService.getMetaDataAndImageForId(Long.parseLong(id));
        if(data != null){
            return ResponseEntity.ok().body(data);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Resource
    public void setMetaDataService(MetaDataServiceImpl metaDataService){
        this.metaDataService=metaDataService;
    }

}
