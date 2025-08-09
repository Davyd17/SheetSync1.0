package com.demo.sheetsync.model.dto.response;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SheetSummaryResponse {

    private Integer sheetId;
    private String title;
    private List<String> headers;
    private String spreadsheetId;
}
