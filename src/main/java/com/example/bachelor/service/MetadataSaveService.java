package com.example.bachelor.service;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataSaveService {

    MetaData saveMetaData(Object metaDataInformation, Long id, MultipartFile image);

    MetaData saveMetaData(Object metaDataInformation, MultipartFile image);

}
