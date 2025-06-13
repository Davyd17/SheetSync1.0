package com.demo.sheetsync.service;

import com.demo.sheetsync.model.dto.response.SheetResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SheetService {
    private final GoogleSheetsService googleSheetsService;
    private final GoogleSheetMapper googleSheetMapper;
    private final SheetMapper sheetMapper;
    private final SheetRepository sheetRepository;

    public List<SheetResponse> saveAllSheets(SpreadSheetApp spreadSheet) {

        List<SheetApp> sheets = googleSheetsService
                .getGoogleSheets(spreadSheet.getSpreadsheetId())
                .stream().map(googleSheet -> {

                    SheetApp sheet = googleSheetMapper.mapToEntity(googleSheet, spreadSheet);

                    sheet.setHeaders(getHeaders(spreadSheet.getSpreadsheetId()));
                    sheet.setRows(getData(sheet));

                    return sheet;

                })
                .toList();

        return sheetRepository.saveAll(sheets)
                .stream().map(sheetMapper::toResponse)
                .toList();
    }


    private List<String> getHeaders(SheetApp sheet) {

        String spreadSheetId = sheet.getSpreadSheet()
                .getSpreadsheetId();

        String sheetTitle = sheet.getTitle();

        List<List<Object>> data = googleSheetsService
                .getData(spreadSheetId,
                        sheetTitle + "!A1:C");

        return data.get(0)
                .stream()
                .map(Object::toString)
                .toList();
    }

    private List<LinkedHashMap<String, Object>> getData(SheetApp sheet){

        String spreadSheetId = sheet.getSpreadSheet()
                .getSpreadsheetId();

        List<LinkedHashMap<String, Object>> data = new ArrayList<>();

        LinkedHashMap<String, Object> row = new LinkedHashMap<>();

        List<List<Object>> googleData = googleSheetsService
                .getData(spreadSheetId, "Hoja 1!A2:C");

            for(List<Object> googleRow : googleData){

                for(int i = 0; i < sheet.getHeaders().size(); i++){

                    row.put(sheet.getHeaders().get(i), googleRow.get(i));

                }

                data.add(row);

        }

    return data;

    }


    }




