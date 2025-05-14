package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.GoogleSheetAccessException;
import com.demo.sheetsync.model.entity.SpreadSheet;
import com.demo.sheetsync.model.entity.dto.mapper.GoogleSpreadsheetMapper;
import com.demo.sheetsync.model.entity.dto.mapper.SpreadSheetDataMapper;
import com.demo.sheetsync.model.entity.dto.request.SpreadSheetDataRequest;
import com.demo.sheetsync.model.entity.dto.response.SpreadSheetDataResponse;
import com.demo.sheetsync.repository.SpreadSheetRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpreadSheetService {

    private final SpreadSheetRepository repository;
    private final Sheets sheets;

    private final GoogleSpreadsheetMapper googleSpreadsheetMapper;

    private final SpreadSheetDataMapper spreadSheetDataMapper;

    private static final Logger logger = LoggerFactory.getLogger(SpreadSheetService.class);

    public SpreadSheetDataResponse saveSpreadSheet(String spreadSheetId)  {

        Spreadsheet googleSpreadSheet = tryGetGoogleSpreadSheet(spreadSheetId);

        SpreadSheet spreadSheet = googleSpreadsheetMapper.maptoEntity(googleSpreadSheet);

        return spreadSheetDataMapper
                .toResponse(repository.save(spreadSheet));

    }

    private Spreadsheet tryGetGoogleSpreadSheet(String spreadSheetId){

        try {

            return sheets.spreadsheets()
                    .get(spreadSheetId)
                    .execute();

        } catch (IOException e){

            String msg = "Something went wrong retrieving spreadsheet with ID: "
                    + spreadSheetId;

            logger.error(msg, e);
            throw new GoogleSheetAccessException(msg, e);
        }

    }


}
