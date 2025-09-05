package com.demo.sheetsync.config.googlesheets;

import com.demo.sheetsync.model.entity.GoogleCredentialsProperties;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GoogleSheetsConfig {

    private final GoogleAuth googleAuth;

    @Bean
    public NetHttpTransport googleNetHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public JsonFactory jsonFactory(){
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    public GoogleCredentialsProperties credentialsProperties(JsonFactory jsonFactory, NetHttpTransport httpTransport) throws GeneralSecurityException, IOException {
            return new GoogleCredentialsProperties(
                    "tokens",
                    Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY),
                    "/credentials.json",
                    jsonFactory,
                    httpTransport
            );
    }

    @Bean
    public Sheets sheetsService(GoogleCredentialsProperties properties) throws IOException {

        return new Sheets.Builder(
                properties.HTTP_TRANSPORT(),
                properties.JSON_FACTORY(),
                googleAuth.getCredentials(properties)
        ).setApplicationName("SheetsSync")
                .build();
    }


    @Bean
    public Drive driveService(GoogleCredentialsProperties properties) throws IOException {

        return new Drive.Builder(
                properties.HTTP_TRANSPORT(),
                properties.JSON_FACTORY(),
                googleAuth.getCredentials(properties)
        ).setApplicationName("SheetsSync")
                .build();
    }

}
