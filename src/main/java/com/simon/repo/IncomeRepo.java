package com.simon.repo;

import com.simon.entity.Income;

import java.util.List;

public interface IncomeRepo {

    public void save( Income income );
    public List<Income> findAll();
}
