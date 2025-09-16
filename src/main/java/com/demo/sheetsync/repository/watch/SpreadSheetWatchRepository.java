package com.demo.sheetsync.repository.watch;

import com.demo.sheetsync.model.entity.SpreadsheetWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadSheetWatchRepository extends JpaRepository<SpreadsheetWatch, String> {
}
