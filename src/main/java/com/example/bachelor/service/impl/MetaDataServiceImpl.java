package com.example.bachelor.service.impl;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.entities.response.ResponseDataWithImageObject;
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
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MetaDataServiceImpl implements MetaDataService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${jwt.header.token}")
    private String REQUEST_HEADER_WITH_TOKEN;
    @Value("${jwt.header.group}")
    private String REQUEST_HEADER_GROUP;

    private MetadataRepository metadataRepository;
    private ImageServiceImpl imageService;
    private SequenceGenerationServiceImpl sequenceGenerationService;
    private JwtUtil jwtUtil;

    @Override
    public MetaData saveMetaData(Object metaDataInformation, long id, MultipartFile image, HttpServletRequest servletRequest){
        try {
             return metadataRepository.save(new MetaData.Builder()
                    .withID(id)
                    .withInformation(metaDataInformation)
                    .withPath(saveImage(image))
                    .withOwner(getUserFromRequest(servletRequest))
                    .withGroup(getGroupFromRequest(servletRequest))
                    .build());
        }catch(IOException e){
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
            return null;
        }
    }

    @Override
    public MetaData saveMetaData(Object metaDataInformation, MultipartFile image, HttpServletRequest servletRequest) {
        try {
            return metadataRepository.save(new MetaData.Builder()
                    .withID(sequenceGenerationService.generateSequence(MetaData.SEQUENCE_NAME))
                    .withInformation(metaDataInformation)
                    .withPath(saveImage(image))
                    .withOwner(getUserFromRequest(servletRequest))
                    .withGroup(getGroupFromRequest(servletRequest))
                    .build());
        }catch (IOException e)
        {
            LOG.error("Error during saving the Image {}. No data were persisted in the database", image.getName(), e);
            return null;
        }
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

    @Override
    public ResponseDataWithImageObject getMetaDataAndImageForId(long _id){
        Optional<MetaData> dataForId = metadataRepository.findBy_id(_id);
        if(dataForId.isPresent()) {
            try {
                return new ResponseDataWithImageObject(imageService.getImage(dataForId.get().getPath()), dataForId.get());
            }catch (IOException e){
                LOG.error("Error occurred during reading image from filesystem!",e);
                return null;
            }
        }else{
            LOG.error("The requested data for the id {} where not present!", _id);
            return null;
        }
    }


    private Path saveImage(MultipartFile image) throws IOException{
        return imageService.saveImage(image);
    }

    @Override
    public String getUserFromRequest(HttpServletRequest httpServletRequest){
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
    public void setImageService(ImageServiceImpl imageService){
        this.imageService = imageService;
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
