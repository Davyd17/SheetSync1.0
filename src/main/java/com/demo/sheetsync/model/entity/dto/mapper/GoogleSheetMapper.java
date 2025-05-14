package com.demo.sheetsync.model.entity.dto.mapper;

import com.demo.sheetsync.model.entity.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class GoogleSheetMapper {

    public static Sheet mapToEntity(com.google.api.services.sheets.v4.model.Sheet googleSheet,
                             Spreadsheet parentGoogleSpreadSheet){

        return Sheet.builder()
                .sheetId(googleSheet.getProperties().getSheetId())
                .title(googleSheet.getProperties().getTitle())
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(GoogleSpreadsheetMapper.maptoEntity(parentGoogleSpreadSheet))
                .build();
    }

}
