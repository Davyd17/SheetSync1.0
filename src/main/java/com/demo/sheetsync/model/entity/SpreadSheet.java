package com.demo.sheetsync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Table(name = "spreadsheet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "spreadsheet_id")
    private String spreadsheetId;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "spreadSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SheetApp> sheets;

}
