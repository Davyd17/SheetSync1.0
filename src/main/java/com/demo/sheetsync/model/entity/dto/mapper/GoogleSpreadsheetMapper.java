package com.demo.sheetsync.model.entity.dto.mapper;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleSpreadsheetMapper {

    private final GoogleSheetMapper googleSheetMapper;

    public SpreadSheet maptoEntity(Spreadsheet googleSpreadsheet){

        return SpreadSheet.builder()
                .spreadsheetId(googleSpreadsheet.getSpreadsheetId())
                .title(googleSpreadsheet.getProperties().getTitle())
                .sheets(googleSpreadsheet.getSheets()
                        .stream()
                        .map(sheet -> googleSheetMapper
                                .mapToEntity(sheet, googleSpreadsheet))
                        .collect(Collectors.toList()))
                .build();
    }
}
