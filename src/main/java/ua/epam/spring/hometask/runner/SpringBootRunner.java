package ua.epam.spring.hometask.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.epam.spring.hometask"})
public class SpringBootRunner {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{SpringBootRunner.class}, args);
    }

}