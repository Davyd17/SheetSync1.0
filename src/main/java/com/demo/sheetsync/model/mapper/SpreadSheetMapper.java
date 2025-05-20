package com.demo.sheetsync.model.mapper;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import org.springframework.stereotype.Component;

@Component
public class SpreadSheetDataMapper {

    public SpreadSheetResponse toResponse(SpreadSheet spreadSheet){

        return new SpreadSheetResponse(
                spreadSheet.getSpreadsheetId(),
                spreadSheet.getTitle(),
                spreadSheet.getSheets()
        );

    }
}
