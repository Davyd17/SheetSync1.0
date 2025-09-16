package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class GoogleSheetMapper {

    public SheetApp mapToEntity(com.google.api.services.sheets.v4.model.Sheet googleSheet,
                                SpreadSheetApp parentSpreadSheet){

        return SheetApp.builder()
                .sheetId(googleSheet.getProperties().getSheetId())
                .title(googleSheet.getProperties().getTitle())
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(parentSpreadSheet)
                .build();
    }

}
