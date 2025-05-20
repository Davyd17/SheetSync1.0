package com.demo.sheetsync.model.dto.response;

import com.demo.sheetsync.model.entity.Sheet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetDataResponse {

    private String spreadsheetId;

    private String title;

    private List<Sheet> sheets;
}
