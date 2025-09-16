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





}
