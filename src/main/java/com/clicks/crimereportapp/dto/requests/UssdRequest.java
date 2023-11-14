package com.clicks.crimereportapp.dto.requests;

public record UssdRequest(
        String sessionId,
        String phoneNumber,
        String networkCode,
        String serviceCode,
        String text
) {
}
