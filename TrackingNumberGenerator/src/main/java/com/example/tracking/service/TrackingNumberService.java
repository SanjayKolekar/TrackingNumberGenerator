package com.example.tracking.service;

import com.example.tracking.dao.TrackingNumberRepository;
import com.example.tracking.entity.TrackingNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Random;

@Service
public class TrackingNumberService {

    @Autowired
    private TrackingNumberRepository repository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TRACKING_NUMBER_LENGTH = 16;

    public TrackingNumber generateTrackingNumber() {
        String trackingNumber;
        do {
            trackingNumber = generateRandomTrackingNumber();
        } while (repository.existsByTrackingNumber(trackingNumber));

        TrackingNumber trackingNumberEntity = new TrackingNumber();
        trackingNumberEntity.setTrackingNumber(trackingNumber);
        trackingNumberEntity.setCreatedAt(OffsetDateTime.now());
        return repository.save(trackingNumberEntity);
    }

    private String generateRandomTrackingNumber() {
        StringBuilder sb = new StringBuilder(TRACKING_NUMBER_LENGTH);
        Random random = new Random();
        for (int i = 0; i < TRACKING_NUMBER_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
