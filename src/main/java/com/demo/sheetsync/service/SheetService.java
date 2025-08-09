package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.NotFoundException;
import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SheetService {
    private final GoogleSheetsService googleSheetsService;
    private final GoogleSheetMapper googleSheetMapper;
    private final SheetMapper sheetMapper;
    private final SheetRepository sheetRepository;

    public List<SheetSummaryResponse> saveAllSheets(SpreadSheetApp spreadSheet) {

        List<SheetApp> sheets = googleSheetsService
                .getGoogleSheets(spreadSheet.getSpreadsheetId())
                .stream().map(googleSheet -> {

                    SheetApp sheet = googleSheetMapper.mapToEntity(googleSheet, spreadSheet);

                    sheet.setHeaders(getGoogleSheetHeaders(sheet));
                    sheet.setRows(getGoogleSheetRows(sheet));

                    return sheet;

                })
                .toList();

        return sheetRepository.saveAll(sheets)
                .stream().map(sheetMapper::toSummaryResponse)
                .toList();
    }

    public List<SheetSummaryResponse> getAllSheetSummaries(){

        return sheetRepository.findAllSheetSummaries();
    }

    public SheetSummaryResponse getSheetSummaryBy(String title){

        return sheetRepository.findSummaryByTitle(title)
                .orElseThrow(() -> new NotFoundException(
                        format("Sheet with title %s not found", title)
                ));
    }

    public Page<LinkedHashMap<String, Object>> getDataFrom(Integer sheetId, Pageable pageable){

        SheetApp sheet = getSheetBy(sheetId);

        List<String> headers = sheet.getHeaders();
        List<List<Object>> rows = sheet.getRows();

        int startPage = (int) pageable.getOffset();
        int endPage = Math.min(startPage + pageable.getPageSize(), rows.size());

        List<List<Object>> pagedRows = rows.subList(startPage, endPage);

        List<LinkedHashMap<String, Object>> mappedPagedRows =
                toRowMap(pagedRows, headers);


        return new PageImpl<>(mappedPagedRows, pageable, rows.size());

    }

    private SheetApp getSheetBy(Integer id){

        return sheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format("Sheet with id %s not found", id)
                ));
    }



    private List<LinkedHashMap<String, Object>> toRowMap(List<List<Object>> rows,
                                                         List<String> headers) {

        List<LinkedHashMap<String, Object>> data = new ArrayList<>();

        for (List<Object> row : rows) {

            LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();

            IntStream.range(0, headers.size())
                    .forEach(i -> {

                        rowData.put(headers.get(i), row.get(i));

                    });

            data.add(rowData);
        }


        return data;

    }


    private List<String> getGoogleSheetHeaders(SheetApp sheet) {

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

    private List<List<Object>> getGoogleSheetRows(SheetApp sheet) {

        return googleSheetsService
                .getData(sheet.getSpreadSheet().getSpreadsheetId(),
                        sheet.getTitle() + "!A2:C"
                );

    }

}




