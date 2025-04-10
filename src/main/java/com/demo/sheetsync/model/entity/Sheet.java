package com.demo.sheetsync.model.entity;

import com.demo.sheetsync.util.JSONBConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Entity
@Table(name = "sheet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Sheet {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "sheet_id")
    private String sheetId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private List<String> headers;

    // Serialize List<LinkedHashMap<String, Object>> as a JSONB PostgreSQL data type
    @Convert(converter = JSONBConverter.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<LinkedHashMap<String, Object>> data;

    @ManyToOne
    @JoinColumn(name = "spreadsheet_id", nullable = false)
    private SpreadSheet spreadSheet;
}
