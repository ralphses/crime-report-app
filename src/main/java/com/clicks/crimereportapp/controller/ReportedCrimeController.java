package com.clicks.crimereportapp.controller;

import com.clicks.crimereportapp.dto.requests.NewCrimeReport;
import com.clicks.crimereportapp.dto.requests.ReportedCrimeDto;
import com.clicks.crimereportapp.dto.requests.UssdRequest;
import com.clicks.crimereportapp.service.ReportedCrimeService;
import com.clicks.crimereportapp.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/report")
public class ReportedCrimeController {

    private final ReportedCrimeService reportedCrimeService;

    @PostMapping("/report")
    public boolean report(@RequestBody NewCrimeReport newCrimeReport) {
        return reportedCrimeService.report(newCrimeReport);
    }

    @GetMapping("/{page}")
    public ResponseEntity<CustomResponse> get(@PathVariable Integer page) {
        List<ReportedCrimeDto> crimes = reportedCrimeService.getAll(page);
        return ResponseEntity.ok(new CustomResponse("SUCCESS", crimes));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> resolve(@PathVariable long id) {
        boolean resolved = reportedCrimeService.resolve(id);
        return ResponseEntity.ok(new CustomResponse("SUCCESS", resolved));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable long id) {
        boolean resolved = reportedCrimeService.delete(id);
        return ResponseEntity.ok(new CustomResponse("SUCCESS", resolved));

    }

    @PostMapping(value = "/handle", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String handleUssdRequest(UssdRequest ussdRequest) {
        return reportedCrimeService.handle(
                ussdRequest);
    }
}
