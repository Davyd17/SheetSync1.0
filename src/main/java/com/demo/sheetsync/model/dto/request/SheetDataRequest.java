package com.demo.sheetsync.model.dto.request;

import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SheetDataRequest {

    private Spreadsheet spreadsheet;
}
