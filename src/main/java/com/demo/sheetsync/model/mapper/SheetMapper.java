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
                .build();
    }

    public SheetApp toEntity(SheetResponse response){

        return SheetApp.builder()
                .sheetId(response.getSheetId())
                .title(response.getTitle())
                .headers(response.getHeaders())
                .rows(response.getRows())
                .build();
    }


}
