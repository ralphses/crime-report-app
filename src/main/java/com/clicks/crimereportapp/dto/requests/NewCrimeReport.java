package com.clicks.crimereportapp.dto.requests;

public record NewCrimeReport(
        String reporter,
        String crimeScene,
        String crimeType,
        String roomNumber,
        String otherDetails,
        String lodgeName,
        String area
) {
}
