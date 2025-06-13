package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {
    private final GoogleSheetsService googleSheetsService;
    private final GoogleSheetMapper googleSheetMapper;
    private final SheetMapper sheetMapper;
    private final SheetRepository sheetRepository;

    public List<SheetResponse> saveAllSheets(SpreadSheetApp spreadSheet) {

        List<SheetApp> sheets = googleSheetsService
                .getGoogleSheets(spreadSheet.getSpreadsheetId())
                .stream().map(googleSheet -> {

                    SheetApp sheet = googleSheetMapper.mapToEntity(googleSheet, spreadSheet);

                    sheet.setHeaders(getHeaders(sheet));

                    return sheet;

                })
                .toList();

        return sheetRepository.saveAll(sheets)
                .stream().map(sheetMapper::toResponse)
                .toList();
    }


    private List<String> getHeaders(SheetApp sheet) {

        String spreadSheetId = sheet.getSpreadSheet()
                .getSpreadsheetId();

        String sheetTitle = sheet.getTitle();

        List<List<Object>> data = googleSheetsService
                .getData(spreadSheetId,
                        sheetTitle + "!A1:C");

        if(data == null || data.isEmpty())
            return new ArrayList<>();

        return data.get(0)
                .stream()
                .map(Object::toString)
                .toList();
    }


    }




