package com.example.sdn6demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class Sdn6DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sdn6DemoApplication.class, args);
    }

}
