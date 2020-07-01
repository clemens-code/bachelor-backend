package com.example.bachelor;

import com.example.bachelor.repository.metadata.MetadataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/*@EnableMongoRepositories(includeFilters = {
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes = {
				MetadataRepository.class
		})
})*/
@SpringBootApplication
public class BachelorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BachelorApplication.class, args);
	}

}
