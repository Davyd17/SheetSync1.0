package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.entity.Sheet;
import com.demo.sheetsync.model.entity.SpreadSheet;
import com.google.api.services.sheets.v4.Sheets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpreadSheetRepositoryTest {

    SpreadSheetRepository spreadSheetRepository;

    @BeforeEach
    void setUp() {


    }
}

