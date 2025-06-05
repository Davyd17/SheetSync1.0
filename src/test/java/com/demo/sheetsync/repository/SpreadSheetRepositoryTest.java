package com.demo.sheetsync.repository;

import org.junit.jupiter.api.BeforeEach;
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

