package com.demo.sheetsync.repository.sheet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SheetJdbcRepositoryImpl implements SheetJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<List<Object>> fetchRowsByColumn(int indexHeader, String criteria) {

        String sql = "SELECT rows_search " +
                "FROM sheet, LATERAL jsonb_array_elements(rows) AS rows_search " +
                "WHERE rows_search->>" + indexHeader + " ILIKE ? || '%'";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> parseJsonRowAsList(rs.getString("rows_search")),
                criteria
        );
    }

    private List<Object> parseJsonRowAsList (String json){

        try {
            return mapper.readValue(json, new TypeReference<List<Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
