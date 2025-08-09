package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.NotFoundException;
import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.mapper.SpreadSheetMapper;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SpreadSheetService {

    private final SpreadSheetRepository repository;
    private final GoogleSpreadsheetMapper googleSpreadsheetMapper;
    private final SpreadSheetMapper spreadSheetMapper;
    private final GoogleSheetsService googleSheetsService;
    private final SheetService sheetService;

    public SpreadSheetResponse saveSpreadSheet(String spreadSheetId)  {

        Spreadsheet googleSpreadSheet = googleSheetsService
                .getGoogleSpreadSheet(spreadSheetId);

        SpreadSheetApp spreadSheet = googleSpreadsheetMapper.maptoEntity(googleSpreadSheet);

        SpreadSheetResponse response = spreadSheetMapper
                .toResponse(repository.save(spreadSheet));

        response.setSheetSummaries(sheetService.saveAllSheets(spreadSheet));

        return response;
    }

    public SpreadSheetResponse getSpreadSheet(String spreadSheetId){

        SpreadSheetApp spreadSheet = repository.findBySpreadsheetId(spreadSheetId)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            format("SpreadSheetApp with id %s not found," +
                                    " please enter a valid one", spreadSheetId)
                    );
                });

        return spreadSheetMapper.toResponse(spreadSheet);
    }



}
