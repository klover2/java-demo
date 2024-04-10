package com.klover.xxl.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * XxlJobApplication
 *
 * @author klover
 * @date 2024/4/7 20:17
 */
@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = "com.klover.xxl.job")
public class XxlJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(XxlJobApplication.class, args);
    }
}
