package com.demo.sheetsync.model.entity;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public record GoogleCredentialsProperties(
        
        String TOKENS_DIRECTORY_PATH, Collection<String> SCOPES,
        String CREDENTIALS_FILE_PATH, JsonFactory JSON_FACTORY,
        NetHttpTransport HTTP_TRANSPORT) {

}
