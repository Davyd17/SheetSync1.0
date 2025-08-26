package com.demo.sheetsync.controller;

import com.demo.sheetsync.model.dto.response.SheetSummaryResponse;
import com.demo.sheetsync.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/sheet")
public class SheetController {

    private final SheetService sheetService;

    @GetMapping("/{id}")
    public ResponseEntity<SheetSummaryResponse> getById(Integer id){

        return ResponseEntity.ok(sheetService.getSheetSummaryBy(id));

    }

    @GetMapping("/{id}/data")
    public Page<LinkedHashMap<String, Object>> getData(@PathVariable Integer id,
                                                       Pageable pageable){

        return sheetService.getDataFrom(id, pageable);
    }

    @GetMapping("{id}/{header}/search")
    public Page<LinkedHashMap<String, Object>> getDataByHeader( @PathVariable Integer id,
                                                                @PathVariable String header,
                                                                @RequestParam String criteria,
                                                                Pageable pageable){

        return sheetService.fetchDataByHeader(id, header, criteria, pageable);
    }
}
