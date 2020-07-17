package com.example.bachelor.repository.metadata;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends MongoRepository<MetaData, Long> {

    List<MetaData> findByOwner(final String owner);

    List<MetaData> findByOwnerAndGroup(final String owner, String group);

    Optional<MetaData> findBy_idAndOwner(final long _id,final String owner);

}
