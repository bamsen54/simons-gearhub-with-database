package com.simon.service;

import com.simon.entity.Income;
import com.simon.repo.IncomeRepo;

public class IncomeService {

    IncomeRepo incomeRepo;


    public IncomeService() {}

    public IncomeService(IncomeRepo incomeRepo) {
        this.incomeRepo = incomeRepo;
    }

    public void addIncome(Income income) {
        incomeRepo.save( income );
    }
}
