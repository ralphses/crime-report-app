package com.clicks.crimereportapp.controller;

import com.clicks.crimereportapp.dto.requests.CrimeSceneDto;
import com.clicks.crimereportapp.service.CrimeSceneService;
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
@RequestMapping(value = "api/v1/crime-scene")
public class CrimeSceneController {

    private final CrimeSceneService crimeSceneService;

    @PostMapping()
    public ResponseEntity<CustomResponse> registerCrime(@RequestBody CrimeSceneDto crimeSceneDto) {
        crimeSceneService.add(crimeSceneDto);
        return ResponseEntity.status(CREATED).body(new CustomResponse("SUCCESS", emptyMap()));
    }

    @GetMapping("/{page}")
    public ResponseEntity<CustomResponse> getCrimeScene(@PathVariable(required = false) Integer page) {
        List<CrimeSceneDto> crimeScenes = crimeSceneService.get(page);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("scenes", crimeScenes)));
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getCrime(
            @RequestParam(name = "area", required = false) String area,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "room", required = false) String room,
            @RequestParam(name = "lodge") String lodge) {
        List<CrimeSceneDto> crimeScenes = crimeSceneService.search(page, area, lodge, room);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("scenes", crimeScenes)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCrime(@PathVariable Long id) {
        crimeSceneService.delete(id);
        return ResponseEntity.status(OK).body(new CustomResponse("SUCCESS", Map.of("status", true)));
    }
}
