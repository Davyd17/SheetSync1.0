package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.model.mapper.SpreadSheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    SheetMapper sheetMapper;

    @Mock 
    SheetRepository sheetRepository;

    @InjectMocks
    SheetService sheetService;

    @Test
    void shouldSaveAllSheetsRelatedToSpreadSheet() {

        //Given
        SpreadSheetApp spreadSheet = SpreadSheetApp.builder()
                .spreadsheetId("mock_id")
                .title("test title")
                .sheets(new ArrayList<>())
                .build();

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

        SheetApp mappedSheet1 = SheetApp.builder()
                .sheetId(1234)
                .title("sheet1 title")
                .rows(new ArrayList<>())
                .headers(new ArrayList<>())
                .spreadSheet(spreadSheet)
                .build();

        SheetApp mappedSheet2 = SheetApp.builder()
                .sheetId(4321)
                .title("sheet2 title")
                .rows(new ArrayList<>())
                .headers(new ArrayList<>())
                .spreadSheet(spreadSheet)
                .build();

        SheetResponse responseMappedSheet1 = new SheetResponse();
        responseMappedSheet1.setSheetId(1234);
        responseMappedSheet1.setTitle("sheet1 title");
        responseMappedSheet1.setHeaders(new ArrayList<>());
        responseMappedSheet1.setRows(new ArrayList<>());

        SheetResponse responseMappedSheet2 = new SheetResponse();
        responseMappedSheet2.setSheetId(4321);
        responseMappedSheet2.setTitle("sheet2 title");
        responseMappedSheet2.setHeaders(new ArrayList<>());
        responseMappedSheet2.setRows(new ArrayList<>());

        when(googleSheetsService.getGoogleSheets(spreadSheet.getSpreadsheetId()))
                .thenReturn(List.of(googleSheet1, googleSheet2));

        when(googleSheetMapper.mapToEntity(googleSheet1, spreadSheet))
                .thenReturn(mappedSheet1);

        when(googleSheetMapper.mapToEntity(googleSheet2, spreadSheet))
                .thenReturn(mappedSheet2);

        when(sheetRepository.saveAll(List.of(mappedSheet1, mappedSheet2)))
                .thenReturn(List.of(mappedSheet1, mappedSheet2));

        when(sheetMapper.toResponse(mappedSheet1))
                .thenReturn(responseMappedSheet1);

        when(sheetMapper.toResponse(mappedSheet2))
                .thenReturn(responseMappedSheet2);

        //When
        List<SheetResponse> savedSheets = sheetService
                .saveAllSheets(spreadSheet);

        //Then
        assertThat(savedSheets).hasSize(2);

        assertEquals(savedSheets.get(0), responseMappedSheet1);
        assertEquals(savedSheets.get(0).getTitle(),
                responseMappedSheet1.getTitle());

        verify(googleSheetsService)
                .getGoogleSheets(spreadSheet.getSpreadsheetId());

        verify(googleSheetMapper, times(2))
                .mapToEntity(any(Sheet.class), any(SpreadSheetApp.class));

        verify(sheetMapper, times(2))
                .toResponse(any(SheetApp.class));

        verify(sheetRepository).saveAll(List.of(
                mappedSheet1,
                mappedSheet2));

    }
}