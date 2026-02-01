package com.simon.repo;

import com.simon.entity.Member;

import java.util.Optional;

public interface MemberRepo extends Repo<Member, Long> {

    public Optional<Member> findByEmail(String email);
}
