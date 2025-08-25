package com.demo.sheetsync.controller;

import com.demo.sheetsync.model.dto.response.SpreadSheetResponse;
import com.demo.sheetsync.service.SpreadSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/spreadsheet")
public class SpreadSheetController {

    private final SpreadSheetService service;

    @PostMapping("persist/{spreadSheetId}")
    public ResponseEntity<SpreadSheetResponse> saveFromGoogleSheet(@PathVariable String spreadSheetId) {

        return ResponseEntity.ok(
                service.saveSpreadSheet(spreadSheetId)
        );

    }

    @GetMapping("{spreadSheetId}")
    public ResponseEntity<SpreadSheetResponse> getBySpreadSheetId(@PathVariable String spreadSheetId){

        return ResponseEntity.ok(
                service.getSpreadSheet(spreadSheetId)
        );
    }
}
