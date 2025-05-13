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
    Sheets sheets;

    @Mock
    Sheets.Spreadsheets spreadsheets;

    @Mock
    Sheets.Spreadsheets.Get getRequest;

    @InjectMocks
    SpreadSheetService service;

    @Test
    void should_save_spreadSheet() throws IOException {

        //Arrange
        String spreadSheetId = "test_spreadSheet_id";

        SpreadSheetDataRequest request =
                new SpreadSheetDataRequest(spreadSheetId);

        Spreadsheet googleSpreadsheet = new Spreadsheet();

        SpreadSheet spreadSheetEntity = new SpreadSheet();

        SpreadSheet savedEntity = new SpreadSheet();

        SpreadSheetDataResponse response =
                new SpreadSheetDataResponse();

        when(sheets.spreadsheets()).thenReturn(spreadsheets);
        when(spreadsheets.get(spreadSheetId)).thenReturn(getRequest);
        when(getRequest.execute()).thenReturn(googleSpreadsheet);

        when(googleSpreadsheetMapper
                .maptoEntity(googleSpreadsheet))
                .thenReturn(spreadSheetEntity);

        when(repository.save(spreadSheetEntity)).thenReturn(savedEntity);

        when(spreadSheetDataMapper
                .toResponse(savedEntity)).thenReturn(response);

        //Act
        SpreadSheetDataResponse result = service.saveSpreadSheet(request);

        //Assert
        assertEquals(response, result);

        verify(sheets.spreadsheets()).get(spreadSheetId);
        verify(getRequest).execute();

        verify(googleSpreadsheetMapper).maptoEntity(googleSpreadsheet);

        verify(repository).save(spreadSheetEntity);

        verify(spreadSheetDataMapper).toResponse(savedEntity);
    }

}