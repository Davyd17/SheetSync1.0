package com.demo.sheetsync.service;

import com.demo.sheetsync.exception.GoogleSheetAccessException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final Sheets sheets;
    private static final Logger logger = LoggerFactory.getLogger(SpreadSheetService.class);


    protected Spreadsheet getGoogleSpreadSheet(String spreadSheetId){

        return tryGetGoogleSpreadSheet(spreadSheetId);
    }

    protected List<Sheet> getGoogleSheets(String spreadSheetId){

        return tryGetGoogleSpreadSheet(spreadSheetId).getSheets();
    }

    protected List<List<Object>> getData(String spreadSheetId, String range){

        ValueRange googleData = tryGetData(spreadSheetId, range);

        return googleData.isEmpty()?
                new ArrayList<>() : googleData.getValues();
    }

    private ValueRange tryGetData(String spreadSheetId, String range){

        try{

            return sheets.spreadsheets()
                    .values()
                    .get(spreadSheetId, range)
                    .execute();

        } catch(IOException e){

            String msg = "Something went wrong retrieving the data from" +
                    "spreadsheet with ID: "
                    + spreadSheetId;

            logger.error(msg, e);
            throw new GoogleSheetAccessException(msg, e);
        }


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
