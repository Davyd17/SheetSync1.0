package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import com.google.api.services.sheets.v4.model.Sheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SheetServiceTest {

    @Mock
    GoogleSheetsService googleSheetsService;

    @Mock
    GoogleSheetMapper googleSheetMapper;

    @Mock
    SheetRepository sheetRepository;

    @InjectMocks
    SheetService sheetService;

    @Test
    void shouldSaveAllSheets() {

        //Arrange
        SpreadSheetApp spreadSheet = SpreadSheetApp.builder()
                .spreadsheetId("id_test")
                .build();

        Sheet googleSheet1 = new Sheet();
        Sheet googleSheet2 = new Sheet();

        List<Sheet> sheets = List.of(googleSheet1, googleSheet2);

        SheetApp mappedSheet1 = new SheetApp();
        SheetApp mappedSheet2 = new SheetApp();

        when(googleSheetsService.getGoogleSheets(spreadSheet.getSpreadsheetId()))
                .thenReturn(sheets);

        when(googleSheetMapper.mapToEntity(googleSheet1, spreadSheet))
                .thenReturn(mappedSheet1);

        when(googleSheetMapper.mapToEntity(googleSheet2, spreadSheet))
                .thenReturn(mappedSheet2);

        //Act
        ArgumentCaptor<SheetApp> captor = ArgumentCaptor.forClass(SheetApp.class);

        sheetService.saveAll(spreadSheet);

        //Assert
        verify(sheetRepository, times(2))
                .save(captor.capture());

        List<SheetApp> savedSheets = captor.getAllValues();
        assertThat(savedSheets).hasSize(2);

        verifyNoMoreInteractions(sheetRepository);

    }
}