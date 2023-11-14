package com.clicks.crimereportapp.controller;

import com.clicks.crimereportapp.dto.CrimeDto;
import com.clicks.crimereportapp.service.CrimeService;
import com.clicks.crimereportapp.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/crime")
public class CrimeController {

    private final CrimeService crimeService;

    @PostMapping("/")
    public ResponseEntity<CustomResponse> registerCrime(@RequestBody CrimeDto crimeDto) {
        crimeService.add(crimeDto);
        return ResponseEntity.status(CREATED).body(new CustomResponse("SUCCESS", emptyMap()));
    }

    @GetMapping("/{page}")
    public ResponseEntity<CustomResponse> getCrime(@PathVariable(required = false) Integer page) {
        List<CrimeDto> crimes = crimeService.get(page);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("crimes", crimes)));
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getCrime(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "description") String description) {
        List<CrimeDto> crimes = crimeService.search(page, name, description);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("crimes", crimes)));
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse> updateCrime(@RequestBody CrimeDto crime) {
        crimeService.update(crime);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("status", true)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCrime(@PathVariable Long id) {
        crimeService.delete(id);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("status", true)));
    }
}
