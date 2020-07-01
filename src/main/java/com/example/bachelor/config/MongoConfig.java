package com.example.bachelor.config;

import com.example.bachelor.entities.metadata.MetaData;
import com.example.bachelor.repository.metadata.MetadataRepository;
import com.mongodb.client.MongoClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = MetadataRepository.class)
@Configuration
public class MongoConfig {

    @Bean
    CommandLineRunner commandLineRunner(MetadataRepository metadataRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                metadataRepository.save(new MetaData((long) 1, "Hallo", "der/Pfad"));
                metadataRepository.save(new MetaData((long) 2, "Ballo", "der/Test"));
                metadataRepository.save(new MetaData((long) 3, "Ciao", "der/Manfred"));
            }
        };
    }
}
