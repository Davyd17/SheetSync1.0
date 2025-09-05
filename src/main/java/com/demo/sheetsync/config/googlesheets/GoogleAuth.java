package com.demo.sheetsync.config.googlesheets;

import com.demo.sheetsync.SheetSyncApplication;
import com.demo.sheetsync.model.entity.GoogleCredentialsProperties;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class GoogleAuth {

    protected Credential getCredentials(final GoogleCredentialsProperties properties)
            throws IOException {

        // Load client secrets.
        InputStream secretsFile = SheetSyncApplication.class.getResourceAsStream(properties.CREDENTIALS_FILE_PATH());

        if (secretsFile == null) {
            throw new FileNotFoundException("Resource not found: " + properties.CREDENTIALS_FILE_PATH());
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(properties.JSON_FACTORY(), new InputStreamReader(secretsFile));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                properties.HTTP_TRANSPORT(), properties.JSON_FACTORY(), clientSecrets, properties.SCOPES())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(properties.TOKENS_DIRECTORY_PATH())))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

}
