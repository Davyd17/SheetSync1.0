package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
public class SpreadSheetServiceIntegrationTest {

    @Autowired
    SpreadSheetRepository repository;

    @MockitoBean
    GoogleSpreadsheetMapper googleSpreadsheetMapper;

    @MockitoBean
    GoogleSheetsService googleSheetsService;

    @MockitoBean
    SheetService sheetService;

    @Autowired
    SpreadSheetService service;


    @Test
    void should_save_and_return_spreadsheet_response() throws IOException {

        //Given
        String spreadSheetId = "test_spreadSheet_id";

        Spreadsheet googleSpreadsheet = new Spreadsheet();
        googleSpreadsheet.setSpreadsheetId("test_spreadSheet_id");
        googleSpreadsheet.setProperties(new SpreadsheetProperties()
                .setTitle("test sheet"));

        SpreadSheetApp spreadSheet = SpreadSheetApp.builder()
                .id(null)
                .spreadsheetId(spreadSheetId)
                .title("test sheet")
                .sheets(new ArrayList<>())
                .build();

        SheetSummaryResponse sheetSummaryResponse1 = new SheetSummaryResponse();
        sheetSummaryResponse1.setSheetId(1234);
        sheetSummaryResponse1.setTitle("sheet1 title");
        sheetSummaryResponse1.setHeaders(new ArrayList<>());

        SheetSummaryResponse sheetSummaryResponse2 = new SheetSummaryResponse();
        sheetSummaryResponse2.setSheetId(4321);
        sheetSummaryResponse2.setTitle("sheet2 title");
        sheetSummaryResponse2.setHeaders(new ArrayList<>());

        when(googleSheetsService
                .getGoogleSpreadSheet(spreadSheetId))
                .thenReturn(googleSpreadsheet);

        //Mock mapper behavior
        when(googleSpreadsheetMapper.maptoEntity(googleSpreadsheet))
                .thenReturn(spreadSheet);

        when(sheetService.saveAllSheets(spreadSheet))
                .thenReturn((List.of(sheetSummaryResponse1, sheetSummaryResponse2)));

        //When
        SpreadSheetResponse result = service.saveSpreadSheet(spreadSheetId);

        //Then
        assertNotNull(result);
        assertEquals("test sheet", result.getTitle());
        assertEquals(spreadSheetId, result.getSpreadsheetId());

        verify(sheetService).saveAllSheets(spreadSheet);
        assertThat(result.getSheetSummaries()).hasSize(2);

        //check persistence
        List<SpreadSheetApp> all = repository.findAll();
        assertEquals(1, all.size());
        SpreadSheetApp saved = all.get(0);
        assertEquals("test sheet", saved.getTitle());
        assertEquals(spreadSheetId, saved.getSpreadsheetId());
    }



}
