package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class SheetMapper {

    public SheetSummaryResponse toSummaryResponse(SheetApp sheet){

        return SheetSummaryResponse.builder()
                .sheetId(sheet.getSheetId())
                .title(sheet.getTitle())
                .headers(sheet.getHeaders())
                .spreadsheetId(sheet.getSpreadSheet().getSpreadsheetId())
                .build();
    }

    public SheetApp toEntity(SheetSummaryResponse response){

        return SheetApp.builder()
                .sheetId(response.getSheetId())
                .title(response.getTitle())
                .headers(response.getHeaders())
                .build();
    }


}
