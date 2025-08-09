package com.demo.sheetsync.controller;

import com.demo.sheetsync.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/sheet")
public class SheetController {

    private final SheetService sheetService;

    @GetMapping("/{sheetId}/data")
    public Page<LinkedHashMap<String, Object>> getData(@PathVariable Integer sheetId,
                                                       Pageable pageable){

        return sheetService.getDataFrom(sheetId, pageable);
    }
}
