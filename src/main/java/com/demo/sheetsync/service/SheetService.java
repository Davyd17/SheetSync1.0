package com.demo.sheetsync.service;

import com.demo.sheetsync.config.googlesheets.GoogleAuth;
import com.demo.sheetsync.model.entity.dto.request.SpreadSheetInfoRequest;
import com.demo.sheetsync.model.entity.dto.response.SheetDataResponse;
import com.demo.sheetsync.repository.SheetRepository;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.temporal.ValueRange;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;

    private final SpreadSheetRepository spreadSheetRepository;

    private final Sheets sheets;

    public SheetDataResponse getSheetData(SpreadSheetInfoRequest spreadSheetRequest, String range){

        return null;
    }




}
