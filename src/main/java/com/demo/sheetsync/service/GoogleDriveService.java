package com.demo.sheetsync.service;

import com.demo.sheetsync.config.properties.DriveProperties;
import com.demo.sheetsync.exception.SpreadsheetWatchException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Channel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final Drive driveService;
    private final DriveProperties driveProperties;

    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);


    public Channel startWatching(String spreadsheetId) {


        Channel channel = new Channel()
                .setId(UUID.randomUUID().toString())
                .setType("web_hook")
                .setAddress(driveProperties.webhookUrl());

        return tryStartWatching(spreadsheetId, channel);

    }

    private Channel tryStartWatching(String spreadsheetId, Channel channel) {

        try{

            return driveService.files()
                    .watch(spreadsheetId, channel)
                    .execute();

        } catch (GoogleJsonResponseException e) {
            logger.error("Google API error while registering watch: {}", e.getDetails(), e);
            throw new RuntimeException("Failed to register watch for spreadsheet: " + spreadsheetId, e);
        } catch (Exception e) {
            logger.error("Unexpected error while registering watch", e);
            throw new RuntimeException("Unexpected error while registering watch", e);
        }


    }
}
