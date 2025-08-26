package com.demo.sheetsync.repository.sheet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SheetJdbcRepository {

    List<List<Object>> fetchRowsByColumn(int indexHeader, String criteria);

}
