package com.clicks.crimereportapp.dto.requests;

public record ReportedCrimeDto(
        String id,
        String name,
        String scene,
        String reporter,
        String dateReported,
        String status
) {
}
