package com.demo.sheetsync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashMap;
import java.util.List;

@Entity
@Table(name = "sheet")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sheet {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "sheet_id")
    private Integer sheetId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private List<String> headers;

    // Serialize List<LinkedHashMap<String, Object>> as a JSONB PostgreSQL rows type using hibernate-types dependency
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<LinkedHashMap<String, Object>> rows;

    @ManyToOne
    @JoinColumn(name = "spreadsheet_id", nullable = false)
    private SpreadSheet spreadSheet;
}
