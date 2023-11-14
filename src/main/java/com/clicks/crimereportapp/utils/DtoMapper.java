package com.clicks.crimereportapp.utils;

import com.clicks.crimereportapp.dto.CrimeDto;
import com.clicks.crimereportapp.dto.requests.CrimeSceneDto;
import com.clicks.crimereportapp.dto.requests.ReportedCrimeDto;
import com.clicks.crimereportapp.model.Crime;
import com.clicks.crimereportapp.model.CrimeScene;
import com.clicks.crimereportapp.model.ReportedCrime;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class DtoMapper {

    public CrimeDto crimeDto(Crime crime) {
        return new CrimeDto(
                crime.getName(),
                crime.getDescription(),
                crime.getCode(),
                crime.getId());
    }

    public CrimeSceneDto crimeSceneDto(CrimeScene crimeScene) {
        return new CrimeSceneDto(
                crimeScene.getRoomNo(),
                crimeScene.getLodgeName(),
                crimeScene.getArea(),
                crimeScene.getCode());
    }

    public ReportedCrimeDto reportedCrimeDto(ReportedCrime reportedCrime) {
        String scene = reportedCrime.getScene().getRoomNo() +
                ", " +
                reportedCrime.getScene().getLodgeName() +
                ", " +
                reportedCrime.getScene().getArea();

        return new ReportedCrimeDto(
                String.valueOf(reportedCrime.getId()),
                reportedCrime.getCrime().getName(),
                scene,
                reportedCrime.getReporter(),
                reportedCrime.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")),
                reportedCrime.isResolved() ? "Resolved" : "Pending"
        );
    }
}
