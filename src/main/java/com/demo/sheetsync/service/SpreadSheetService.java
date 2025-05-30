package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.NotFoundException;
import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.mapper.SpreadSheetMapper;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        SpreadSheet spreadSheet = googleSpreadsheetMapper.maptoEntity(googleSpreadSheet);

        SpreadSheetResponse response = spreadSheetMapper
                .toResponse(repository.save(spreadSheet));

        sheetService.saveAll(spreadSheet);

        return response;

    }

    public SpreadSheetResponse getSpreadSheet(String spreadSheetId){

        SpreadSheet spreadSheet = repository.findBySpreadsheetId(spreadSheetId)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            format("SpreadSheet with id %s not found," +
                                    " please enter a valid one", spreadSheetId)
                    );
                });

        return spreadSheetMapper.toResponse(spreadSheet);
    }



}
