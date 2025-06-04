package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SpreadSheetMapper {

    public SpreadSheetResponse toResponse(SpreadSheetApp spreadSheet){

        return new SpreadSheetResponse(
                spreadSheet.getSpreadsheetId(),
                spreadSheet.getTitle(),
                spreadSheet.getSheets()
        );

    }

    public SpreadSheetApp toEntity(SpreadSheetResponse response){

        return SpreadSheetApp.builder()
                .spreadsheetId(response.getSpreadsheetId())
                .title(response.getTitle())
                .sheets(new ArrayList<>())
                .build();

    }
}
