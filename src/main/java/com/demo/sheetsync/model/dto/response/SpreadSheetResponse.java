package com.demo.sheetsync.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetResponse {

    private String spreadsheetId;

    private String title;

    private List<SheetSummaryResponse> sheetSummaries;
}
