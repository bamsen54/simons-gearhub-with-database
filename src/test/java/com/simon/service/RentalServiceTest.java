package com.simon.service;

import com.simon.entity.*;
import com.simon.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    private RentalService rentalService;

    @Mock private RentalRepo rentalRepo;
    @Mock private MemberRepo memberRepo;
    @Mock private BikeRepo bikeRepo;
    @Mock private KayakRepo kayakRepo;
    @Mock private TentRepo tentRepo;
    @Mock private InventoryService inventoryService;
    @Mock private IncomeService incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalService = new RentalService(rentalRepo, memberRepo, bikeRepo, kayakRepo, tentRepo, inventoryService, incomeService);
    }

    @Test
    @DisplayName("Should return true when bike status is AVAILABLE")
    void should_ReturnTrue_When_BikeIsAvailable() {
        Bike availableBike = new Bike();
        availableBike.setStatus(ItemStatus.AVAILABLE);
        when(bikeRepo.findById(1L)).thenReturn(Optional.of(availableBike));

        boolean result = rentalService.isItemAvailable(RentalType.BIKE, 1L);

        assertThat(result).isTrue();
        verify(bikeRepo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should set item status to UNAVAILABLE when processing new rental")
    void should_SetStatusToUnavailable_When_ProcessingNewRental() {
        Bike testBike = new Bike();
        testBike.setStatus(ItemStatus.AVAILABLE);
        when(bikeRepo.findById(10L)).thenReturn(Optional.of(testBike));

        Rental rental = new Rental();
        rental.setRentalType(RentalType.BIKE);
        rental.setRentalObjectId(10L);

        rentalService.processNewRental(rental);

        assertThat(testBike.getStatus()).isEqualTo(ItemStatus.UNAVAILABLE);
        verify(bikeRepo, times(1)).update(testBike);
        verify(rentalRepo, times(1)).save(rental);
    }
}