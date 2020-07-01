package com.example.bachelor;

import com.example.bachelor.repository.metadata.MetadataRepository;
import com.example.bachelor.repository.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableMongoRepositories(basePackageClasses = MetadataRepository.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@SpringBootApplication
public class BachelorApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(BachelorApplication.class, args);
	}

}
