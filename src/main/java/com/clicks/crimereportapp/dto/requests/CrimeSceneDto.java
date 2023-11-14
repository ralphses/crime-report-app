package com.clicks.crimereportapp.dto.requests;

public record CrimeSceneDto(
        String roomNumber,
        String lodgeName,
        String area,
        long code

) {
}
