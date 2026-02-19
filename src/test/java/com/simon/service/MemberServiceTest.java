package com.simon.service;

import com.simon.entity.Member;
import com.simon.entity.Rental;
import com.simon.exception.RemoveMemberWithActiveRentalsException;
import com.simon.repo.MemberRepo;
import com.simon.service.IncomeService;
import com.simon.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepo memberRepo;

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private MemberService memberService;



    @Test
    @DisplayName("Should successfully save a member and retrieve it by ID")
    void should_AddAndRetrieveMember_When_DataIsValid() {
        Member testMember = new Member("Jane", "Doe", "jane.doe@prison.com");

        when(memberRepo.findById(1L)).thenReturn(Optional.of(testMember));

        memberService.addMember(testMember);
        Optional<Member> found = memberService.findById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Jane");

        verify(memberRepo, times(1)).findById(1L);
        verify(memberRepo, times(1)).save(testMember);
    }

    @Test
    @DisplayName("Should throw exception when deleting a member with active rentals")
    void should_ThrowException_When_DeletingMemberWithActiveRentals() {
        Member testMember = mock(Member.class); // Vi mockar medlemmen för att enkelt styra rentals

        when(testMember.getRentals()).thenReturn(java.util.List.of(new Rental()));

        assertThatThrownBy(() -> memberService.deleteMember(testMember))
                .isInstanceOf(RemoveMemberWithActiveRentalsException.class)
                .hasMessageContaining("still has active rentals");

        verify(memberRepo, never()).delete(any(Member.class));
    }
}