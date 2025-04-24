package com.demo.sheetsync.config.googlesheets;

import com.demo.sheetsync.model.entity.GoogleSheetsProperties;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
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
    public GoogleSheetsProperties googleSheetsProperties(JsonFactory jsonFactory, NetHttpTransport httpTransport) throws GeneralSecurityException, IOException {
            return new GoogleSheetsProperties(
                    "tokens",
                    Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY),
                    "/credentials.json",
                    jsonFactory,
                    httpTransport
            );
    }

    @Bean
    public Sheets sheetsService(GoogleSheetsProperties properties) throws IOException {

        return new Sheets.Builder(
                properties.getHTTP_TRANSPORT(),
                properties.getJSON_FACTORY(),
                googleAuth.getCredentials(properties)
        ).setApplicationName("SheetsSync")
                .build();
    }

}
