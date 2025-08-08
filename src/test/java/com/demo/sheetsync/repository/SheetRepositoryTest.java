package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.entity.SheetApp;
import com.demo.sheetsync.model.entity.SpreadSheetApp;
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

        //We save a spreadsheet before interact with the SheetApp to apply the relation between them
        spreadSheetRepository.save(SpreadSheetApp.builder()
                .spreadsheetId("spreadSheetMockId")
                .title("testTitle")
                .build());
    }


    @Test
    void shouldSaveSheet(){

        //Given
        List<String> headers = List.of("name", "last name", "address");

        //Each row
        List<Object> row = List.of("Alex","Doe","20 Street");

        //The whole rows that will be saved
        List<List<Object>> data = new ArrayList<>();

        data.add(row);

        //Get the SpreadSheetApp saved previously
        SpreadSheetApp spreadSheet = spreadSheetRepository
                .findById(1)
                .orElseThrow(() -> new NotFoundException(
                        "spreadSheet Not Found"
                ));

        //Save the SheetApp with the related SpreadSheetApp saved previously
        SheetApp sheet = SheetApp.builder()
                .sheetId(1234)
                .headers(headers)
                .spreadSheet(spreadSheet)
                .title("testTittle")
                .rows(data)
                .build();

        //When
        sheetRepository.save(sheet);

        //Then
        SheetApp savedSheet = sheetRepository.findById(1)
                .orElseThrow(() -> new NotFoundException(
                        "SheetApp not found"
                ));

        assertThat(savedSheet.getRows()).isEqualTo(data);
    }
}