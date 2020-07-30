package com.example.bachelor.service.impl;


import com.example.bachelor.dto.metadata.ReturnMetaData;
import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.security.util.JwtUtil;
import com.example.bachelor.service.MetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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
        if (metadataRepository.findBy_id(id).isPresent()){
            throw new UnsupportedOperationException("An Entity for the ID "+id+" already exits! If you want to create a new Entity use put " +
                    "with another id or post without an id. If you want to update the Entity please use the endpoint /data/manipulate/update/{id}");
        }
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
    public List<ReturnMetaData> getAllMetaData(HttpServletRequest httpServletRequest){
        return mapListMetaDataOnReturnMetaData(metadataRepository.findByOwner(getUserFromRequest(httpServletRequest)));
    }

    @Override
    public List<ReturnMetaData> getAllMetaDataByGroup(HttpServletRequest httpServletRequest) {
       return mapListMetaDataOnReturnMetaData(metadataRepository.findByOwnerAndGroup(getUserFromRequest(httpServletRequest), getGroupFromRequest(httpServletRequest)));

    }

    @Override
    public List<ReturnMetaData> getAllMetaDataByGroup(String group, HttpServletRequest httpServletRequest) {
       return mapListMetaDataOnReturnMetaData(metadataRepository.findByOwnerAndGroup(getUserFromRequest(httpServletRequest), group));
    }

    @Override
    public Optional<ReturnMetaData> getMetaDataById(long _id, HttpServletRequest servletRequest) {
        return metadataRepository.findBy_idAndOwner(_id, getUserFromRequest(servletRequest)).map(this::mapMetaDataOnReturnMetaData);

    }

    @Override
    public void deleteEntityByID(long _id, HttpServletRequest servletRequest){
        Optional<MetaData> metaData = metadataRepository.findBy_id(_id);
        if(metaData.isEmpty()){
            throw new IllegalArgumentException("There is no entity present for the image!");
        }
        if(isUserPermitted(metaData.get(), servletRequest)) {
            LOG.error("User {} not permitted to delete entity of User {}", getUserFromRequest(servletRequest), metaData.get().owner);
            throw new UnsupportedOperationException("You are not permitted to update this entity!");
        }

        metadataRepository.delete(metaData.get());

        if (metadataRepository.findBy_id(_id).isPresent()){
            LOG.error("Deleting the Entity was not possible!");
            throw new UnknownError("Deleting the Entity was not possible!");
        }
        try{
            imageService.deleteImage(metaData.get().getPath());
        }catch (IOException e){
            LOG.error("Unexpected Error during deleting the image for MetaData {}", _id, e);
            throw new UnknownError("Deleting the Image was not possible!");
        }


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

    @Override
    public void updateMetaData(@Nullable Object metaDataInformation,
                               long id, @Nullable MultipartFile image,
                               HttpServletRequest servletRequest){
        Optional<MetaData> metaData = metadataRepository.findBy_id(id);
        Path newPath = null;

        if(metaData.isEmpty()){
            throw new IllegalArgumentException("There is no entity present for the image!");
        }
        if(isUserPermitted(metaData.get(), servletRequest)){
            throw new UnsupportedOperationException("You are not permitted to update this entity!");
        }
        if(metaDataInformation == null && image == null){
            throw new IllegalArgumentException("The image or the metadata must not be null to update!");
        }
        if(image != null){
            try{
                imageService.deleteImage(metaData.get().getPath());
                newPath = saveImage(image);
            }catch (IOException e){
                LOG.error("Error during update the image {}", id);
            }
        }
        if(metaDataInformation != null) {
            if(newPath == null) {
                newPath=metaData.get().getPath();
            }
            metadataRepository.save(new MetaData.Builder().withID(id)
                    .withInformation(metaDataInformation)
                    .withOwner(getUserFromRequest(servletRequest))
                    .withPath(newPath)
                    .withGroup(getGroupFromRequest(servletRequest))
                    .build());
        }
    }

    @Override
    public String getUserFromRequest(HttpServletRequest httpServletRequest){
        return jwtUtil.extractUsername(httpServletRequest.getHeader(REQUEST_HEADER_WITH_TOKEN).substring(7));
    }

    @Override
    public ReturnMetaData mapMetaDataOnReturnMetaData(MetaData metaData){
        return new ReturnMetaData.Builder().withID(metaData._id)
                                            .withInformation(metaData.information)
                                            .withGroup(metaData.group)
                                            .withOwner(metaData.owner)
                                            .build();
    }

    private List<ReturnMetaData> mapListMetaDataOnReturnMetaData(List<MetaData> metaDatas){
        List<ReturnMetaData> returnMetaDatas = new ArrayList<>();
        for (MetaData metaData : metaDatas){
            returnMetaDatas.add(mapMetaDataOnReturnMetaData(metaData));
        }
        return returnMetaDatas;
    }

    private boolean isUserPermitted(MetaData metaData, HttpServletRequest servletRequest){
        return !metaData.owner.equals(getUserFromRequest(servletRequest));
    }

    private Path saveImage(MultipartFile image) throws IOException{
        return imageService.saveImage(image);
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
