package com.small.rose.lite.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class SmallLiteArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallLiteArchiveApplication.class, args);
    }

}
