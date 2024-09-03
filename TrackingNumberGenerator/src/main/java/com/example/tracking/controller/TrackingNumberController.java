package com.example.tracking.controller;

import com.example.tracking.entity.TrackingNumber;
import com.example.tracking.service.TrackingNumberService;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;


@RestController
@Validated
public class TrackingNumberController {

    private static final String ISO_COUNTRY_CODE_PATTERN = "^[A-Z]{2}$";
    private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final String SLUG_PATTERN = "^[a-z0-9]+(-[a-z0-9]+)*$";

    @Autowired
    private TrackingNumberService service;

    @GetMapping("/next-tracking-number")
    public ResponseEntity<Map<String, String>> getNextTrackingNumber(
            @RequestParam @Pattern(regexp = ISO_COUNTRY_CODE_PATTERN, message = "Invalid origin country code") String origin_country_id,
            @RequestParam @Pattern(regexp = ISO_COUNTRY_CODE_PATTERN, message = "Invalid destination country code") String destination_country_id,
            @RequestParam @DecimalMin(value = "0.0", inclusive = true, message = "Weight must be positive") @DecimalMax(value = "999.999", inclusive = true, message = "Weight must not exceed 999.999") Double weight,
            @RequestParam String created_at,
            @RequestParam @Pattern(regexp = UUID_PATTERN, message = "Invalid UUID format") String customer_id,
            @RequestParam @NotEmpty(message = "Customer name cannot be empty") String customer_name,
            @RequestParam @Pattern(regexp = SLUG_PATTERN, message = "Invalid slug format") String customer_slug) {

        created_at = created_at.trim().replace(" ", "+");

        OffsetDateTime createdAt;
        try {
            createdAt = OffsetDateTime.parse(created_at);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of("created_at", "Invalid RFC 3339 timestamp format"));
        }

        TrackingNumber trackingNumber = service.generateTrackingNumber();

        Map<String, String> response = new HashMap<>();
        response.put("tracking_number", trackingNumber.getTrackingNumber());
        response.put("created_at", trackingNumber.getCreatedAt().toString());

        return ResponseEntity.ok(response);
    }


}
