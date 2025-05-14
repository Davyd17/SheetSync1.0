package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.entity.dto.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.entity.dto.request.SpreadSheetDataRequest;
import com.demo.sheetsync.model.entity.dto.response.SpreadSheetDataResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.Sheets;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
public class SpreadSheetServiceIntegrationTest {

    @Autowired
    SpreadSheetRepository repository;

    @Autowired
    SpreadSheetService service;

    @MockitoBean
    GoogleSpreadsheetMapper googleMapper;

    @MockitoBean
    Sheets sheets;

    @Test
    void should_save_and_return_spreadsheet_response() throws IOException {

        //Given
        String spreadSheetId = "test_spreadSheet_id";

        Spreadsheet googleSpreadsheet = new Spreadsheet();
        googleSpreadsheet.setSpreadsheetId("test_spreadSheet_id");
        googleSpreadsheet.setProperties(new SpreadsheetProperties()
                .setTitle("test sheet"));

        SpreadSheet entity = SpreadSheet.builder()
                .id(null)
                .spreadsheetId(spreadSheetId)
                .title("test sheet")
                .sheets(new ArrayList<>())
                .build();

        //Mock Sheets
        Sheets.Spreadsheets spreadsheets = mock(Sheets.Spreadsheets.class);
        Sheets.Spreadsheets.Get get = mock(Sheets.Spreadsheets.Get.class);

        when(sheets.spreadsheets()).thenReturn(spreadsheets);
        when(spreadsheets.get(spreadSheetId)).thenReturn(get);
        when(get.execute()).thenReturn(googleSpreadsheet);

        //Mock mapper behavior
        when(googleMapper.maptoEntity(googleSpreadsheet)).thenReturn(entity);

        //When
        SpreadSheetDataResponse result = service.saveSpreadSheet(spreadSheetId);

        //Then
        assertNotNull(result);
        assertEquals("test sheet", result.getTitle());
        assertEquals(spreadSheetId, result.getSpreadsheetId());

        //check persistence
        List<SpreadSheet> all = repository.findAll();
        assertEquals(1, all.size());
        SpreadSheet saved = all.get(0);
        assertEquals("test sheet", saved.getTitle());
        assertEquals(spreadSheetId, saved.getSpreadsheetId());
    }



}
