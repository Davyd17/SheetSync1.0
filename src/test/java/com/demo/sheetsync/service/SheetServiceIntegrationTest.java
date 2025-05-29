package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
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
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class SheetServiceIntegrationTest {

    @MockitoBean
    GoogleSheetsService googleSheetsService;

    @MockitoBean
    GoogleSheetMapper googleSheetMapper;

    @Autowired
    SpreadSheetRepository spreadSheetRepository;

    @Autowired
    SheetRepository sheetRepository;

    @Autowired
    SheetService sheetService;

    List<Sheet> googleSheets;

    @BeforeEach
    void setUp() {

        //Arrange

        Sheet googleSheet1 = new Sheet();
        SheetProperties sheet1Props = new SheetProperties();
        sheet1Props.setSheetId(1234);
        sheet1Props.setTitle("sheet1_title");
        googleSheet1.setProperties(sheet1Props);

        Sheet googleSheet2 = new Sheet();
        SheetProperties sheet2Props = new SheetProperties();
        sheet2Props.setSheetId(4321);
        sheet2Props.setTitle("sheet2_title");
        googleSheet2.setProperties(sheet2Props);

        googleSheets = List.of(googleSheet1, googleSheet2);
    }

    @Test
    void should_save_all_sheet_related_to_spreadsheet(){


        //Arrange

        SpreadSheetApp spreadSheet = new SpreadSheetApp();
        spreadSheet.setSpreadsheetId("test_id");
        spreadSheet.setTitle("test spreadsheet");

        spreadSheetRepository.save(spreadSheet);

        SheetApp mappedSheet1 = SheetApp.builder()
                .sheetId(1234)
                .title("sheet1_title")
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(spreadSheet)
                .build();

        SheetApp mappedSheet2 = SheetApp.builder()
                .sheetId(4321)
                .title("sheet2_title")
                .headers(new ArrayList<>())
                .rows(new ArrayList<>())
                .spreadSheet(spreadSheet)
                .build();

        when(googleSheetsService.getGoogleSheets(spreadSheet
                .getSpreadsheetId())).thenReturn(googleSheets);


        when(googleSheetMapper.mapToEntity(googleSheets.get(0), spreadSheet))
                .thenReturn(mappedSheet1);

        when(googleSheetMapper.mapToEntity(googleSheets.get(1), spreadSheet))
                .thenReturn(mappedSheet2);

        //Act
        sheetService.saveAll(spreadSheet);

        //Assert
        List<SheetApp> savedSheet = sheetRepository.findAll();

        assertThat(savedSheet).hasSize(2);

        assertEquals(mappedSheet1.getSheetId(), savedSheet.get(0)
                .getSheetId());

        assertEquals(mappedSheet2.getSheetId(), savedSheet.get(1)
                .getSheetId());

    }

}