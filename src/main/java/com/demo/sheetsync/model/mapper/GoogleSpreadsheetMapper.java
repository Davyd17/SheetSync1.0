package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class GoogleSpreadsheetMapper {

    public SpreadSheetApp maptoEntity(Spreadsheet googleSpreadsheet){

        return SpreadSheetApp.builder()
                .spreadsheetId(googleSpreadsheet.getSpreadsheetId())
                .title(googleSpreadsheet.getProperties().getTitle())
                .sheets(new ArrayList<>())
                .build();
    }
}
