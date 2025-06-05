package com.demo.sheetsync.model.dto.response;


import com.demo.sheetsync.model.entity.SpreadSheetApp;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SheetResponse {

    private Integer sheetId;
    private String title;
    private List<String> headers;
    private List<LinkedHashMap<String, Object>> rows;
}
