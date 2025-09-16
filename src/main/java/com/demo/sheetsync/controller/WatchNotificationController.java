package com.demo.sheetsync.controller;

import com.demo.sheetsync.service.SpreadSheetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/notify")
public class WatchNotificationController {

    private static final Logger logger = LoggerFactory.getLogger(WatchNotificationController.class);


    @PostMapping
    public ResponseEntity<Void> notification(
            @RequestHeader("X-Goog-Channel-ID") String channelId,
            @RequestHeader("X-Goog-Resource-ID") String resourceId,
            @RequestHeader("X-Goog-Resource-State") String resourceState,
            @RequestHeader("X-Goog-Message-Number") String messageNumber)
    {

        logger.info("Notification received: channelId={}, resourceId={}, resourceState={}, messageNumber={}",
        channelId, resourceId, resourceState, messageNumber);

        return ResponseEntity.ok().build();
    }


}
