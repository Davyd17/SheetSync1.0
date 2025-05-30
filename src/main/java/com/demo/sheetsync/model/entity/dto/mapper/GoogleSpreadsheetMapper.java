<<<<<<< Updated upstream:src/main/java/com/demo/sheetsync/model/entity/dto/mapper/GoogleSpreadsheetMapper.java
package com.demo.sheetsync.model.entity.dto.mapper;
=======
package com.demo.sheetsync.model.mapper;
>>>>>>> Stashed changes:src/main/java/com/demo/sheetsync/model/mapper/GoogleSpreadsheetMapper.java

import com.demo.sheetsync.model.entity.SpreadSheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleSpreadsheetMapper {

    public SpreadSheet maptoEntity(Spreadsheet googleSpreadsheet){

        return SpreadSheet.builder()
                .spreadsheetId(googleSpreadsheet.getSpreadsheetId())
                .title(googleSpreadsheet.getProperties().getTitle())
                .sheets(new ArrayList<>())
                .build();
    }
}
