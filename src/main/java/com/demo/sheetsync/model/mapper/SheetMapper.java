package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.dto.response.SheetResponse;
import org.springframework.stereotype.Component;

@Component
public class SheetMapper {

    public SheetResponse toResponse(SheetApp sheet){

        return SheetResponse.builder()
                .sheetId(sheet.getSheetId())
                .title(sheet.getTitle())
                .headers(sheet.getHeaders())
                .rows(sheet.getRows())
                .spreadSheet(sheet.getSpreadSheet())
                .build();
    }

    public SheetApp toEntity(SheetResponse response){

        return new SheetApp();
    }


}
