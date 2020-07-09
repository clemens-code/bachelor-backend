package com.example.bachelor.service.impl;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.security.util.JwtUtil;
import com.example.bachelor.service.MetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MetaDataServiceImpl implements MetaDataService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageSaveServiceImpl.class);

    @Value("${jwt.header.token}")
    private String REQUEST_HEADER_WITH_TOKEN;
    @Value("${jwt.header.group}")
    private String REQUEST_HEADER_GROUP;

    private MetadataRepository metadataRepository;
    private ImageSaveServiceImpl imageSaveService;
    private SequenceGenerationServiceImpl sequenceGenerationService;
    private JwtUtil jwtUtil;

    @Override
    public MetaData saveMetaData(Object metaDataInformation, long id, MultipartFile image, HttpServletRequest servletRequest) {
        try {
            MetaData metaData = new MetaData.Builder()
                    .withID(id)
                    .withInformation(metaDataInformation)
                    .withPath(saveImage(image))
                    .withOwner(getUserFromRequest(servletRequest))
                    .withGroup(getGroupFromRequest(servletRequest))
                    .build();
            return metadataRepository.save(metaData);
        }catch(IOException e){
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
        }
        return null;
    }

    @Override
    public MetaData saveMetaData(Object metaDataInformation, MultipartFile image, HttpServletRequest servletRequest) {
        try {
            MetaData metaData = new MetaData.Builder()
                    .withID(sequenceGenerationService.generateSequence(MetaData.SEQUENCE_NAME))
                    .withInformation(metaDataInformation)
                    .withPath(saveImage(image))
                    .withOwner(getUserFromRequest(servletRequest))
                    .withGroup(getGroupFromRequest(servletRequest))
                    .build();
            return metadataRepository.save(metaData);
        }catch (IOException e)
        {
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
        }
        return null;
    }

    @Override
    public List<MetaData> getAllMetaData(HttpServletRequest httpServletRequest){
        return metadataRepository.findByOwner(getUserFromRequest(httpServletRequest));
    }

    @Override
    public List<MetaData> getAllMetaDataByGroup(HttpServletRequest httpServletRequest) {
        return metadataRepository.findByOwnerAndGroup(getUserFromRequest(httpServletRequest), getGroupFromRequest(httpServletRequest));
    }

    @Override
    public Optional<MetaData> getMetaDataById(long _id) {
        return metadataRepository.findBy_id(_id);
    }


    private String saveImage(MultipartFile image) throws IOException{
        return imageSaveService.saveImage(image);
    }

    private String getUserFromRequest(HttpServletRequest httpServletRequest){
        return jwtUtil.extractUsername(httpServletRequest.getHeader(REQUEST_HEADER_WITH_TOKEN).substring(7));
    }

    private String getGroupFromRequest(HttpServletRequest httpServletRequest){
        String group = httpServletRequest.getHeader(REQUEST_HEADER_GROUP);
        return Objects.requireNonNullElse(group, "default");
    }

    @Resource
    public void setSequenceGenerationService(SequenceGenerationServiceImpl sequenceGenerationService){
        this.sequenceGenerationService=sequenceGenerationService;
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

    @Resource
    public void setJwtUtil(JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }
}
