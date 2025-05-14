package com.demo.sheetsync.model.entity.dto.mapper;

import com.demo.sheetsync.model.entity.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class GoogleSheetMapper {

    private final GoogleSpreadsheetMapper googleSpreadsheetMapper;

    public Sheet mapToEntity(com.google.api.services.sheets.v4.model.Sheet googleSheet,
                             Spreadsheet parentGoogleSpreadSheet){

        return Sheet.builder()
                .sheetId(googleSheet.getProperties().getSheetId())
                .title(googleSheet.getProperties().getTitle())
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(googleSpreadsheetMapper.maptoEntity(parentGoogleSpreadSheet))
                .build();
    }

}
