package com.demo.sheetsync.model.entity.mapper;

import com.demo.sheetsync.model.entity.Sheet;
import com.demo.sheetsync.model.entity.SpreadSheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class GoogleSheetMapper {

    public Sheet mapToEntity(com.google.api.services.sheets.v4.model.Sheet googleSheet,
                             SpreadSheet parentSpreadSheet){

        return Sheet.builder()
                .sheetId(googleSheet.getProperties().getSheetId())
                .title(googleSheet.getProperties().getTitle())
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(parentSpreadSheet)
                .build();
    }

}
