package com.demo.sheetsync.model.entity;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class GoogleSheetsProperties {

    private final String TOKENS_DIRECTORY_PATH;
    private final Collection<String> SCOPES;
    private final String CREDENTIALS_FILE_PATH;
    private final JsonFactory JSON_FACTORY;
    private final NetHttpTransport HTTP_TRANSPORT;
}
