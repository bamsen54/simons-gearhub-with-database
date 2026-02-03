package com.simon.service;

import com.simon.entity.Member;
import com.simon.exception.EmailAlreadyTakenException;
import com.simon.exception.RemoveMemberWithActiveRentalsException;
import com.simon.repo.MemberRepo;
import com.simon.repo.MemberRepoImpl;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private MemberRepo memberRepo;
    private IncomeService incomeService;

    public MemberService() {}

    public MemberService(MemberRepo memberRepo, IncomeService incomeService) {
        this.memberRepo = memberRepo;
        this.incomeService = incomeService;
    }

    public void addMember(Member member) {
        try {
            memberRepo.save(member);
        } catch (EmailAlreadyTakenException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(Member member) throws  EmailAlreadyTakenException {
        try {
            Optional<Member> existingMember = memberRepo.findByEmail(member.getEmail());

            if ( existingMember.isPresent() && !existingMember.get().getId().equals(member.getId() ) )
                throw new EmailAlreadyTakenException("Email " + member.getEmail() + " is already taken by another member.");

            memberRepo.update( member );
        }

        catch ( EmailAlreadyTakenException e ) {
            e.printStackTrace();
            System.err.println( e.getMessage() );
        }
    }

    public Optional<Member> findById(Long id) {
        return memberRepo.findById(id);
    }

    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepo.findByEmail(email);
    }

    public boolean memberWithEmailExist(String email) {
        return memberRepo.findByEmail(email).isPresent();
    }

    public void deleteMember(Member member) {
        if (!member.getRentals().isEmpty())
            throw new RemoveMemberWithActiveRentalsException(member + " still has active rentals");

        try {
            memberRepo.delete(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}