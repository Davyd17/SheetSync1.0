package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.model.mapper.SpreadSheetMapper;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpreadSheetServiceTest {

    @Mock
    SpreadSheetRepository repository;

    @Mock
    GoogleSpreadsheetMapper googleSpreadsheetMapper;

    @Mock
    SpreadSheetMapper spreadSheetMapper;

    @Mock
    GoogleSheetsService googleSheetsService;

    @Mock
    SheetService sheetService;

    @Mock
    SheetMapper sheetMapper;

    @InjectMocks
    SpreadSheetService service;

    @Test
    void should_save_spreadSheet() throws IOException {

        //Arrange
        String spreadSheetId = "test_spreadSheet_id";

        Spreadsheet googleSpreadsheet = new Spreadsheet();

        SpreadSheetApp spreadSheet = new SpreadSheetApp();

        SpreadSheetApp savedSpreadSheet = new SpreadSheetApp();

        SpreadSheetResponse spreadSheetResponse =
                new SpreadSheetResponse();

        SheetApp savedSheet1 = new SheetApp();

        SheetApp savedSheet2 = new SheetApp();

        SheetSummaryResponse sheetSummaryResponse1 = new SheetSummaryResponse();

        SheetSummaryResponse sheetSummaryResponse2 = new SheetSummaryResponse();

        when(googleSheetsService.getGoogleSpreadSheet(spreadSheetId))
                .thenReturn(googleSpreadsheet);

        when(googleSpreadsheetMapper
                .maptoEntity(googleSpreadsheet))
                .thenReturn(spreadSheet);

        when(sheetService.saveAllSheets(spreadSheet)).thenReturn(
                List.of(sheetSummaryResponse1, sheetSummaryResponse2)
        );

        when(sheetMapper.toEntity(sheetSummaryResponse1)).thenReturn(savedSheet1);

        when(sheetMapper.toEntity(sheetSummaryResponse2)).thenReturn(savedSheet2);

        when(repository.save(spreadSheet)).thenReturn(savedSpreadSheet);

        when(spreadSheetMapper
                .toResponse(savedSpreadSheet)).thenReturn(spreadSheetResponse);

        //Act
        SpreadSheetResponse result = service.saveSpreadSheet(spreadSheetId);

        //Assert
        assertEquals(spreadSheetResponse, result);

        verify(googleSheetsService).getGoogleSpreadSheet(spreadSheetId);

        verify(googleSpreadsheetMapper).maptoEntity(googleSpreadsheet);

        verify(sheetService).saveAllSheets(spreadSheet);

        verify(sheetMapper, times(2)).toEntity(any(SheetSummaryResponse.class));

        verify(repository).save(spreadSheet);

        verify(spreadSheetMapper).toResponse(savedSpreadSheet);
    }

}