package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.mapper.SpreadSheetMapper;
import com.demo.sheetsync.repository.SpreadSheetRepository;
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
    SpreadSheetMapper spreadSheetMapper;

    @Mock
    GoogleSheetsService googleSheetsService;

    @InjectMocks
    SpreadSheetService service;

    @Test
    void should_save_spreadSheet() throws IOException {

        //Arrange
        String spreadSheetId = "test_spreadSheet_id";

        Spreadsheet googleSpreadsheet = new Spreadsheet();

        SpreadSheetApp spreadSheetEntity = new SpreadSheetApp();

        SpreadSheetApp savedEntity = new SpreadSheetApp();

        SpreadSheetResponse response =
                new SpreadSheetResponse();

        when(googleSheetsService.getGoogleSpreadSheet(spreadSheetId))
                .thenReturn(googleSpreadsheet);

        when(googleSpreadsheetMapper
                .maptoEntity(googleSpreadsheet))
                .thenReturn(spreadSheetEntity);

        when(repository.save(spreadSheetEntity)).thenReturn(savedEntity);

        when(spreadSheetMapper
                .toResponse(savedEntity)).thenReturn(response);

        //Act
        SpreadSheetResponse result = service.saveSpreadSheet(spreadSheetId);

        //Assert
        assertEquals(response, result);

        verify(googleSheetsService).getGoogleSpreadSheet(spreadSheetId);

        verify(googleSpreadsheetMapper).maptoEntity(googleSpreadsheet);

        verify(repository).save(spreadSheetEntity);

        verify(spreadSheetMapper).toResponse(savedEntity);
    }

}