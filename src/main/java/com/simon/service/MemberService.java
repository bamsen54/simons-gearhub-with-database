package com.simon.service;

import com.simon.entity.Member;
import com.simon.exception.EmailAlreadyTakenException;
import com.simon.exception.RemoveMemberWithActiveRentalsException;
import com.simon.repo.MemberRepoImpl;

import java.util.Optional;

public class MemberService {

    private MemberRepoImpl memberRepo;
    private IncomeService incomeService;

    public MemberService() {}

    public MemberService(MemberRepoImpl memberRepo, IncomeService incomeService) {
        this.memberRepo = memberRepo;
    }

    public void addMember( Member member ) {

        try {
            memberRepo.save( member );
            // todo income service
        }

        catch( EmailAlreadyTakenException e ) {
            e.printStackTrace();
        }
    }

    public void updateMember( Member member ) {
        try {
            memberRepo.update( member );
        }

        catch( EmailAlreadyTakenException e ) {
            e.printStackTrace();
        }
    }

    public Optional<Member> findMemberByEmail(String email ) {

        return memberRepo.findByEmail( email );
    }

    public boolean memberWithEmailExist( String email ) {
        return memberRepo.findByEmail( email ).isPresent();
    }

    public void deleteMember( Member member ) {

        if( !member.getRentals().isEmpty() )
            throw new RemoveMemberWithActiveRentalsException( member + " still has active rentals");

        try {
            memberRepo.delete( member );
        }

        catch( Exception e ) {
            e.printStackTrace();
        }
    }
}
