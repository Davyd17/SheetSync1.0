package com.demo.sheetsync.model.entity.dto.mapper;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleSpreadsheetMapper {

    public SpreadSheet maptoEntity(Spreadsheet googleSpreadsheet){

        return SpreadSheet.builder()
                .spreadsheetId(googleSpreadsheet.getSpreadsheetId())
                .title(googleSpreadsheet.getProperties().getTitle())
                .sheets(new ArrayList<>())
                .build();
    }
}
