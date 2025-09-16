package com.demo.sheetsync.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/*
Entity to store the watch information provided by the Drive API.

The watch enables us to receive notifications when a user performs any changes to a sheet.
 */

@Entity
@Table (name = "sheet_watches")
@Builder
@Getter
@Setter
public class SpreadsheetWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chanel_id", unique = true, nullable = false)
    private String channelId;

    @Column(name = "resourceId", unique = true, nullable = false)
    private String resourceId;

    @Column(nullable = false)
    private Instant expiration;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
