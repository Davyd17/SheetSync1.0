package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.entity.SpreadSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpreadSheetRepository extends JpaRepository<SpreadSheet, Integer> {

    Optional<SpreadSheet> findBySpreadsheetId(String spreadsheetId);

}
