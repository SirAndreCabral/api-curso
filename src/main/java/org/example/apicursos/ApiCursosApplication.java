package org.example.apicursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCursosApplication.class, args);
        System.out.println("http://localhost:8080");
        System.out.println("http://localhost:8080/swagger-ui/index.html");
    }
}
