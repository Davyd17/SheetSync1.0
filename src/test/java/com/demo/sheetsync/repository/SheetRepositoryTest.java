package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.entity.Sheet;
import com.demo.sheetsync.model.entity.SpreadSheet;
import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SheetRepositoryTest {

    @Autowired
    SpreadSheetRepository spreadSheetRepository;

    @Autowired
    SheetRepository sheetRepository;

    @BeforeEach
    void setUp() {

        //We save a spreadsheet before interact with the Sheet to apply the relation between them
        spreadSheetRepository.save(SpreadSheet.builder()
                .spreadsheetId("spreadSheetMockId")
                .title("testTitle")
                .build());
    }


    @Test
    void shouldSaveSheet(){

        //Given
        List<String> headers = List.of("name", "last name", "address");

        //Each row
        LinkedHashMap<String, Object> row = new LinkedHashMap<>();

        //The whole rows that will be saved
        List<LinkedHashMap<String, Object>> data = new ArrayList<>();

        row.put(headers.get(0), "Alex");
        row.put(headers.get(1), "Doe");
        row.put(headers.get(2), "20 Street");

        data.add(row);

        //Get the SpreadSheet saved previously
        SpreadSheet spreadSheet = spreadSheetRepository
                .findById(1)
                .orElseThrow(() -> new NotFoundException(
                        "spreadSheet Not Found"
                ));

        //Save the Sheet with the related SpreadSheet saved previously
        Sheet sheet = Sheet.builder()
                .sheetId(1234)
                .headers(headers)
                .spreadSheet(spreadSheet)
                .title("testTittle")
                .rows(data)
                .build();

        //When
        sheetRepository.save(sheet);

        //Then
        Sheet savedSheet = sheetRepository.findById(1)
                .orElseThrow(() -> new NotFoundException(
                        "Sheet not found"
                ));

        assertThat(savedSheet.getRows()).isEqualTo(data);
    }
}