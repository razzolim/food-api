package com.razzolim.food;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.razzolim.food.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FoodApiApplication {

    public static void main(String[] args) {
	
	/* seta timezone default da aplicação para UTC */
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	
	SpringApplication.run(FoodApiApplication.class, args);
    }

}
