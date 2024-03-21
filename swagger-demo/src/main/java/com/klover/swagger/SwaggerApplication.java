package com.klover.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * SwaggerApplication
 *
 * @author klover
 * @date 2024/3/21 9:46
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = "com.klover.swagger")
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
