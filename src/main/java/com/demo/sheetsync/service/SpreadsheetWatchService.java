package com.demo.sheetsync.service;

import com.demo.sheetsync.model.entity.SpreadsheetWatch;
import com.demo.sheetsync.repository.watch.SpreadSheetWatchRepository;
import com.google.api.services.drive.model.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class SpreadsheetWatchService {

    private final GoogleDriveService googleDriveService;
    private final SpreadSheetWatchRepository repository;

    public SpreadsheetWatch registerWatch(String spreadsheetId){

        Channel channel = googleDriveService.startWatching(spreadsheetId);

        SpreadsheetWatch spreadsheetWatch = SpreadsheetWatch.builder()
                .channelId(channel.getId())
                .resourceId(channel.getResourceId())
                .expiration(handleExpiration(channel.getExpiration()))
                .createdAt(Instant.now())
                .build();

        return repository.save(spreadsheetWatch);
    }

    private Instant handleExpiration(Long expiration){

        return expiration != null
                ? Instant.ofEpochMilli(expiration)
                : Instant.now().plus(7, ChronoUnit.DAYS);
    }

}
