package com.zz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.zz")
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
