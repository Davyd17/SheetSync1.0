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



}