package com.xworkz.xworkzsheetsapi.GoogleSheetsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.xworkz.xworkzsheetsapi.dto.SheetsDto;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "Xworkz-API";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    
    private final Sheets sheetsService;

    public GoogleSheetsService() throws IOException, GeneralSecurityException {
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(SCOPES);
		 HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
			        credentials);
		sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, requestInitializer)
	            .setApplicationName(APPLICATION_NAME)
	            .build();
    }

    public List<SheetsDto> readData(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        List<SheetsDto> dtos = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            return dtos;
        }
        for (List<Object> row : values) {
            SheetsDto dto = new SheetsDto();
            if(row.size()>=1) {
            	dto.setName((String) row.get(0));	
            }
            if(row.size()>=2) {
            	dto.setEmail((String) row.get(1));	
            }
            if(row.size()>=3) {
            	dto.setPhoneNumber((String) row.get(1));	
            }
           
            dtos.add(dto);
        }
        return dtos;
    }
   






    public void writeData(String spreadsheetId, String range, SheetsDto dto) throws IOException {
        List<List<Object>> values = new ArrayList<>();
        List<Object> row = new ArrayList<>();
        row.add(dto.getName());
        row.add(dto.getEmail());
        row.add(dto.getPhoneNumber());
        values.add(row);

        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, range, body)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }

    
    public void clearData(String spreadsheetId, String range) throws IOException {
        sheetsService.spreadsheets().values()
                .clear(spreadsheetId, range, new ClearValuesRequest())
                .execute();
    }
}