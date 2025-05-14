package com.demo.sheetsync.model.entity.dto.mapper;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.entity.dto.response.SpreadSheetDataResponse;
import org.springframework.stereotype.Component;

@Component
public class SpreadSheetDataMapper {

    public SpreadSheetDataResponse toResponse(SpreadSheet spreadSheet){

        return new SpreadSheetDataResponse(
                spreadSheet.getSpreadsheetId(),
                spreadSheet.getTitle(),
                spreadSheet.getSheets()
        );

    }
}
