package com.example.bachelor.repository.metadata;

import com.example.bachelor.entities.metadata.MetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetadataRepository extends MongoRepository<MetaData, Long> {
}
