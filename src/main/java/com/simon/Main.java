package com.simon;

import com.simon.entity.Member;
import com.simon.repo.MemberRepoImpl;
import com.simon.util.HibernateUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class Main  {

    void main() {

        MemberRepoImpl memberRepo = new MemberRepoImpl();

        Member member = new Member( "Billy Mobby", "Grey", "billie.mobby.grey@hollywood.biz" );

        try {
            memberRepo.save(member);
        }

        catch(Exception e) {

        }


    }
}
