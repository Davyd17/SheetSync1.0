package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SpreadSheetMapper {

    private final SheetMapper sheetMapper;

    public SpreadSheetResponse toResponse(SpreadSheetApp spreadSheet){

        return new SpreadSheetResponse(
                spreadSheet.getSpreadsheetId(),
                spreadSheet.getTitle(),
                spreadSheet.getSheets().stream()
                        .map(sheetMapper::toSummaryResponse)
                        .toList()
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
