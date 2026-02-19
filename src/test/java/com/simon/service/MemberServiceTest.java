package com.simon.service;

import com.simon.entity.Member;
import com.simon.repo.MemberRepo;
import com.simon.service.IncomeService;
import com.simon.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testServiceLogic() {
        // 1. Arrange
        Member testMember = new Member("Jane", "Doe", "jane.doe@prison.com");

        // Säg åt REPOT vad det ska svara när servicen frågar efter ID 1
        when(memberRepo.findById(1L)).thenReturn(Optional.of(testMember));

        // 2. Act
        memberService.addMember(testMember);
        // Om du vill testa findById-logiken i din service:
        Optional<Member> found = memberService.findById(1L);

        // 3. Assert
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Jane");

        // Verifiera att servicen faktiskt pratade med repot
        verify(memberRepo, times(1)).findById(1L);
        verify(memberRepo, times(1)).save(testMember);
    }
}