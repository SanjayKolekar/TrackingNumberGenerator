package com.example.tracking.service;

import com.example.tracking.dao.TrackingNumberRepository;
import com.example.tracking.entity.TrackingNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackingNumberServiceTest {

    @Mock
    private TrackingNumberRepository repository;

    @InjectMocks
    private TrackingNumberService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateTrackingNumber_shouldReturnUniqueTrackingNumber() {
        // Arrange
        String newTrackingNumber = "DEF4567890123456";

        // Mock the repository behavior
        when(repository.existsByTrackingNumber(anyString())).thenReturn(false);

        // Mock the saving behavior
        TrackingNumber expectedEntity = new TrackingNumber();
        expectedEntity.setTrackingNumber(newTrackingNumber);
        expectedEntity.setCreatedAt(OffsetDateTime.now());

        when(repository.save(any(TrackingNumber.class))).thenReturn(expectedEntity);

        // Act
        TrackingNumber result = service.generateTrackingNumber();

        // Assert
        assertNotNull(result);
        assertEquals(newTrackingNumber, result.getTrackingNumber());
        assertNotNull(result.getCreatedAt());

        verify(repository, times(1)).save(any(TrackingNumber.class));
        verify(repository, atLeastOnce()).existsByTrackingNumber(anyString());
    }

    @Test
    void generateRandomTrackingNumber_shouldReturnStringOfCorrectLength() {
        // Act
        String trackingNumber = service.generateRandomTrackingNumber();

        // Assert
        assertNotNull(trackingNumber);
        assertEquals(16, trackingNumber.length());
        assertTrue(trackingNumber.matches("[A-Z0-9]+"));
    }
}
