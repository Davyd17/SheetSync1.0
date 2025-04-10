package com.demo.sheetsync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "spreadsheet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String spreadsheet_id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "spreadSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sheet> sheets;

}
