package com.demo.sheetsync;

import com.demo.sheetsync.config.properties.DriveProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableConfigurationProperties(DriveProperties.class)
@SpringBootApplication
public class SheetSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SheetSyncApplication.class, args);
    }

}