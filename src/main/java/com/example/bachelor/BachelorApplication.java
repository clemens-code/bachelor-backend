package com.example.bachelor;

import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackageClasses = MetadataRepository.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@SpringBootApplication
public class BachelorApplication {

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BachelorApplication.class, args);
	}

}
