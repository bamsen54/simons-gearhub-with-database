package com.simon.service;

import com.simon.entity.Bike;
import com.simon.entity.ItemStatus;
import com.simon.entity.Kayak;
import com.simon.repo.BikeRepo;
import com.simon.repo.KayakRepo;
import com.simon.repo.TentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @Mock
    private BikeRepo bikeRepo;

    @Mock
    private KayakRepo kayakRepo;

    @Mock
    private TentRepo tentRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventoryService = new InventoryService(bikeRepo, kayakRepo, tentRepo);
    }

    @Test
    @DisplayName("Should return a bike with correct name when finding by ID")
    void should_ReturnCorrectBike_When_FindingById() {
        Bike testBike = new Bike("Skeppshult", "City", 7, new BigDecimal("100"), ItemStatus.AVAILABLE);
        when(bikeRepo.findById(1L)).thenReturn(Optional.of(testBike));

        Bike result = inventoryService.findById(Bike.class, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Skeppshult");
        verify(bikeRepo, times(1)).findById(1L);
        verifyNoInteractions(kayakRepo, tentRepo);
    }

    @Test
    @DisplayName("Should return a list of all kayaks from the kayak repository")
    void should_ReturnListOfKayaks_When_FindingAll() {
        List<Kayak> kayakList = List.of(new Kayak(), new Kayak());
        when(kayakRepo.findAll()).thenReturn(kayakList);

        List<Kayak> result = inventoryService.findAll(Kayak.class);

        assertThat(result).hasSize(2);
        verify(kayakRepo, times(1)).findAll();
        verifyNoInteractions(bikeRepo, tentRepo);
    }
}