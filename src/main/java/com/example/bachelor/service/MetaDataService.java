package com.example.bachelor.service;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface MetaDataService {

    MetaData saveMetaData(Object metaDataInformation, long id, MultipartFile image, HttpServletRequest servletRequest);

    MetaData saveMetaData(Object metaDataInformation, MultipartFile image, HttpServletRequest servletRequest);

    List<MetaData> getAllMetaData(HttpServletRequest httpServletRequest);

    List<MetaData> getAllMetaDataByGroup(HttpServletRequest httpServletRequest);

    Optional<MetaData> getMetaDataById(long _id);

}
