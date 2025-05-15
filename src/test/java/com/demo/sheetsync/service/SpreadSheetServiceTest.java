package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.entity.dto.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.entity.dto.mapper.SpreadSheetDataMapper;
import com.demo.sheetsync.model.entity.dto.request.SpreadSheetDataRequest;
import com.demo.sheetsync.model.entity.dto.response.SpreadSheetDataResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpreadSheetServiceTest {

    @Mock
    SpreadSheetRepository repository;

    @Mock
    GoogleSpreadsheetMapper googleSpreadsheetMapper;

    @Mock
    SpreadSheetDataMapper spreadSheetDataMapper;

    @Mock
    GoogleSheetsService googleSheetsService;

    @Mock
    Spreadsheet spreadsheet;

    @InjectMocks
    SpreadSheetService service;

    @Test
    void should_save_spreadSheet() throws IOException {

        //Arrange
        String spreadSheetId = "test_spreadSheet_id";

        Spreadsheet googleSpreadsheet = new Spreadsheet();

        SpreadSheet spreadSheetEntity = new SpreadSheet();

        SpreadSheet savedEntity = new SpreadSheet();

        SpreadSheetDataResponse response =
                new SpreadSheetDataResponse();

        when(googleSheetsService.getGoogleSpreadSheet(spreadSheetId))
                .thenReturn(googleSpreadsheet);

        when(googleSpreadsheetMapper
                .maptoEntity(googleSpreadsheet))
                .thenReturn(spreadSheetEntity);

        when(repository.save(spreadSheetEntity)).thenReturn(savedEntity);

        when(spreadSheetDataMapper
                .toResponse(savedEntity)).thenReturn(response);

        //Act
        SpreadSheetDataResponse result = service.saveSpreadSheet(spreadSheetId);

        //Assert
        assertEquals(response, result);

        verify(googleSheetsService).getGoogleSpreadSheet(spreadSheetId);

        verify(googleSpreadsheetMapper).maptoEntity(googleSpreadsheet);

        verify(repository).save(spreadSheetEntity);

        verify(spreadSheetDataMapper).toResponse(savedEntity);
    }

}