package com.simon.service;

import com.simon.entity.Income;
import com.simon.repo.IncomeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class IncomeServiceTest {

    private IncomeService incomeService;

    @Mock
    private IncomeRepo incomeRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        incomeService = new IncomeService(incomeRepo);
    }

    @Test
    @DisplayName("Should call save on incomeRepo when adding income")
    void should_CallSave_When_AddingIncome() {
        Income testIncome = new Income(new BigDecimal("500.00"), LocalDateTime.now(), null);

        incomeService.addIncome(testIncome);

        verify(incomeRepo, times(1)).save(testIncome);
    }
}