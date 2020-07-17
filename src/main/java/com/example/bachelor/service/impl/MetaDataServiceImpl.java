package com.example.bachelor.service.impl;


import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.security.util.JwtUtil;
import com.example.bachelor.service.MetaDataService;
import org.bson.internal.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
    public Optional<MetaData> getMetaDataById(long _id, HttpServletRequest servletRequest) {
        return metadataRepository.findBy_idAndOwner(_id, getUserFromRequest(servletRequest));
    }

    @Override
    public byte[] getImageForId(long _id, HttpServletRequest servletRequest){
        Optional<MetaData> metaData = metadataRepository.findBy_idAndOwner(_id, getUserFromRequest(servletRequest));
        if(metaData.isPresent()){
            try{
                return imageService.getImage(metaData.get().getPath());
            }catch (IOException ex){
                LOG.error("There where no image present for {}",_id, ex);
            }
        }
        return new byte[0];
    }

    public String getImage(long _id, HttpServletRequest servletRequest){
        Optional<MetaData> metaData = metadataRepository.findBy_idAndOwner(_id, getUserFromRequest(servletRequest));
        if(metaData.isPresent()){
            try{
                BufferedImage image = ImageIO.read(new File(metaData.get().path));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", outputStream);
                return Base64.encode(outputStream.toByteArray());
            }catch (IOException ex){
                LOG.error("There where no image present for {}",_id, ex);
            }
        }
        return null;
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
