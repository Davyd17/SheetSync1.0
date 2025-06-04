package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.repository.SheetRepository;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@Transactional
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class SheetServiceIntegrationTest {

    @MockitoBean
    GoogleSheetsService googleSheetsService;

    @Autowired
    SpreadSheetRepository spreadSheetRepository;

    @Autowired
    SheetRepository sheetRepository;

    @Autowired
    SheetService sheetService;

    private SpreadSheetApp spreadSheet;

    @BeforeEach
    void setUp() {

        spreadSheet = SpreadSheetApp.builder()
                .spreadsheetId("mock_id")
                .title("test title")
                .sheets(new ArrayList<>())
                .build();

        spreadSheetRepository.save(spreadSheet);
    }

    @Test
    void should_save_all_sheet_related_to_spreadsheet(){


        //Arrange
        Sheet googleSheet1 = new Sheet();
        SheetProperties sheetProps1 = new SheetProperties();
        sheetProps1.setTitle("sheet1 title");
        sheetProps1.setSheetId(1234);
        googleSheet1.setProperties(sheetProps1);

        Sheet googleSheet2 = new Sheet();
        SheetProperties sheetProps2 = new SheetProperties();
        sheetProps2.setTitle("sheet2 title");
        sheetProps2.setSheetId(4321);
        googleSheet2.setProperties(sheetProps2);

        when(googleSheetsService.getGoogleSheets(spreadSheet.getSpreadsheetId()))
                .thenReturn(List.of(googleSheet1, googleSheet2));

        //Act
        List<SheetResponse> savedSheets = sheetService.saveAllSheets(spreadSheet);

        //Assert
        List<SheetApp> persistSheets = sheetRepository.findAll();

        verify(googleSheetsService).getGoogleSheets(spreadSheet.getSpreadsheetId());

        assertThat(savedSheets).hasSize(2);
        assertEquals(savedSheets.size(), persistSheets.size());

        assertEquals(savedSheets.get(0).getTitle(),
                persistSheets.get(0).getTitle());
    }

}