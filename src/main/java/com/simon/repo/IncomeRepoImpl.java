package com.simon.repo;

import com.simon.entity.Income;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class IncomeRepoImpl implements IncomeRepo {


    @Override
    public void save(Income income) {
        try( Session session = HibernateUtil.getSessionFactory().openSession() ){

            Transaction transaction = session.beginTransaction();
            session.save( income );
            transaction.commit();

        }
    }

    @Override
    public List<Income> findAll() {

        List<Income> incomes = new ArrayList<>();
        try( Session session = HibernateUtil.getSessionFactory().openSession() ){

            incomes = session.createQuery( "from Income " ).list();
        }

        return incomes;
    }
}
