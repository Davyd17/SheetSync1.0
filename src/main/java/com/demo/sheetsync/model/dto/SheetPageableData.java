package com.demo.sheetsync.model.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record SheetPageableData(

        Integer id,
        List<String> headers,
        List<List<Object>> rows,
        Pageable pageable
){}
