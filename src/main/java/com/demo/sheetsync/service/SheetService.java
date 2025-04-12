package com.demo.sheetsync.service;

import com.demo.sheetsync.auth.GoogleAuth;
import com.demo.sheetsync.repository.SheetRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;

    private Sheets provideSheetService() throws GeneralSecurityException, IOException {

        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(
                httpTransport,
                GsonFactory.getDefaultInstance(),
                GoogleAuth.getCredentials(httpTransport)
        ).build();
    }

    


}
