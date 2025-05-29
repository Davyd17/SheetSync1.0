package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {
    private final GoogleSheetsService googleSheetsService;
    private final GoogleSheetMapper googleSheetMapper;
    private final SheetRepository sheetRepository;

    public void saveAll(SpreadSheetApp spreadSheet) {

        List<SheetApp> sheets = googleSheetsService
                .getGoogleSheets(spreadSheet.getSpreadsheetId())
                .stream().map(sheet -> {

                    return googleSheetMapper.mapToEntity(sheet, spreadSheet);

                })
                .toList();

        sheets.forEach(sheetRepository::save);
    }

    public List<SheetApp> findAllBy(String spreadSheetId){

        return null;

    }




}
