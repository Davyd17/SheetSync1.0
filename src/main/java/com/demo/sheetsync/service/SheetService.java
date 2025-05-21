package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.Sheet;
import com.demo.sheetsync.model.entity.SpreadSheet;
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

    public void saveAll(SpreadSheet spreadSheet) {

        List<Sheet> sheets = googleSheetsService
                .getGoogleSheets(spreadSheet.getSpreadsheetId())
                .stream().map(sheet -> {

                    return googleSheetMapper.mapToEntity(sheet, spreadSheet);

                })
                .toList();

        sheets.forEach(sheetRepository::save);
    }

    public List<Sheet> findAllBy(String spreadSheetId){

        return null;

    }




}
