package com.xworkz.xworkzsheetsapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xworkz.xworkzsheetsapi.GoogleSheetsService.GoogleSheetsService;
import com.xworkz.xworkzsheetsapi.dto.SheetsDto;

@RestController
@RequestMapping("/api/sheets")
public class GoogleSheetsController {
	
	@Autowired
    private final GoogleSheetsService sheetsService;
	
	private String range = "A2:C10";

    public GoogleSheetsController(GoogleSheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    @GetMapping("/{spreadsheetId}")
    public ResponseEntity<List<SheetsDto>> readData(
            @PathVariable String spreadsheetId) throws IOException {

        List<SheetsDto> data = sheetsService.readData(spreadsheetId, range);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/{spreadsheetId}")
    public ResponseEntity<Void> writeData(
            @PathVariable String spreadsheetId,
            @RequestBody SheetsDto values) throws IOException {

        sheetsService.writeData(spreadsheetId, range, values);
        return ResponseEntity.ok().build();
    }

   

    @DeleteMapping("/{spreadsheetId}")
    public ResponseEntity<Void> clearData(
            @PathVariable String spreadsheetId,
            @RequestParam String range) throws IOException {

        sheetsService.clearData(spreadsheetId, range);
        return ResponseEntity.ok().build();
    }
}