package com.example.bachelor.service.impl;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.service.ImageSaveService;
import com.example.bachelor.service.MetadataSaveService;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Service
public class MetadataSaveServiceImpl implements MetadataSaveService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageSaveServiceImpl.class);

    private MetadataRepository metadataRepository;

    private ImageSaveServiceImpl imageSaveService;

    @Override
    public MetaData saveMetaData(Object metaDataInformation, Long id, MultipartFile image) {

        try {
            return metadataRepository.save(new MetaData(id, metaDataInformation, saveImage(image)));
        }catch(IOException e){
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
        }
        return null;
    }

    @Override
    public MetaData saveMetaData(Object metaDataInformation, MultipartFile image) {
        try {
            return metadataRepository.save(new MetaData(metaDataInformation, saveImage(image)));
        }catch (IOException e)
        {
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
        }
        return null;
    }

    private String saveImage(MultipartFile image) throws IOException{
        return imageSaveService.saveImage(image);
    }

    @Resource
    public void setImageSaveService(ImageSaveServiceImpl imageSaveService){
        this.imageSaveService=imageSaveService;
    }


    @Resource
    public void setMetadataRepository(MetadataRepository metadataRepository)
    {
        this.metadataRepository=metadataRepository;
    }
}
