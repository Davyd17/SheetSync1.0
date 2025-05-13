package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.entity.dto.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.entity.dto.mapper.SpreadSheetDataMapper;
import com.demo.sheetsync.model.entity.dto.request.SpreadSheetDataRequest;
import com.demo.sheetsync.model.entity.dto.response.SpreadSheetDataResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpreadSheetService {

    private final SpreadSheetRepository repository;
    private final GoogleSpreadsheetMapper googleSpreadsheetMapper;
    private final SpreadSheetDataMapper spreadSheetDataMapper;
    private final Sheets sheets;

    public SpreadSheetDataResponse saveSpreadSheet(SpreadSheetDataRequest request) throws IOException {

        Spreadsheet googleSpreadSheet = sheets.spreadsheets()
                .get(request.getSpreadSheetId())
                .execute();

        SpreadSheet spreadSheet = googleSpreadsheetMapper.maptoEntity(googleSpreadSheet);

        return spreadSheetDataMapper
                .toResponse(repository.save(spreadSheet));

    }


}
