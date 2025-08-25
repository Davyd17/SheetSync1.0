package com.demo.sheetsync.repository;

import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.model.entity.SheetApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SheetRepository extends JpaRepository<SheetApp, Integer> {

    @Query("""
            SELECT new com.demo.sheetsync.model.dto.response.SheetSummaryResponse(
                s.id, s.title, s.headers, s.spreadSheet.spreadsheetId
            )
            FROM SheetApp s
            """)
    Optional<SheetSummaryResponse> findSummaryById(Integer id);

    @Query("""
            SELECT new com.demo.sheetsync.model.dto.response.SheetSummaryResponse(
                s.id, s.title, s.headers, s.spreadSheet.spreadsheetId
            )
            FROM SheetApp s
            """)
    List<SheetSummaryResponse> findAllSheetSummaries();


}
