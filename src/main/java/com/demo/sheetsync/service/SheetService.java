package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.NotFoundException;
import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
import com.demo.sheetsync.model.mapper.GoogleSheetMapper;
import com.demo.sheetsync.model.mapper.SheetMapper;
import com.demo.sheetsync.repository.SheetRepository;
import com.demo.sheetsync.repository.sheet.SheetJdbcRepository;
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
    private final SheetJdbcRepository sheetJdbcRepository;

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

    public SheetSummaryResponse getSheetSummaryBy(Integer id){

        return sheetRepository.findSummaryById(id)
                .orElseThrow(() -> new NotFoundException(
                        format("Sheet with title %s not found", id)
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

    public Page<LinkedHashMap<String, Object>> fetchDataByHeader(Integer id,
                                                                 String header,
                                                                 String criteria,
                                                                 Pageable pageable) {

        List<String> headers = getSheetSummaryBy(id).getHeaders();

        boolean isHeaderPresent = headers.stream()
                .anyMatch(sheetHeader -> sheetHeader.equalsIgnoreCase(header));

        if(isHeaderPresent){

            List<List<Object>> data = sheetJdbcRepository
                    .fetchRowsByColumn(headers.indexOf(header), criteria);

            return pageData(headers, data, pageable);

        } else {

            throw new NotFoundException(format("Header %s not found, please enter a valid one", header));

        }
    }

    private SheetApp getSheetBy(Integer id){

        return sheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format("Sheet with id %s not found", id)
                ));
    }


    private Page<LinkedHashMap<String, Object>> pageData(List<String> headers,
                                                         List<List<Object>> rows,
                                                         Pageable pageable){

        int startPage = (int) pageable.getOffset();
        int endPage = Math.min(startPage + pageable.getPageSize(), rows.size());

        List<List<Object>> pagedRows = rows.subList(startPage, endPage);

        List<LinkedHashMap<String, Object>> mappedPagedRows =
                toRowMap(pagedRows, headers);


        return new PageImpl<>(mappedPagedRows, pageable, rows.size());

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
                        sheetTitle + "!A1:J");

        return data.get(0)
                .stream()
                .map(Object::toString)
                .toList();
    }

    private List<List<Object>> getGoogleSheetRows(SheetApp sheet) {

        return googleSheetsService
                .getData(sheet.getSpreadSheet().getSpreadsheetId(),
                        sheet.getTitle() + "!A2:J"
                );

    }


}




