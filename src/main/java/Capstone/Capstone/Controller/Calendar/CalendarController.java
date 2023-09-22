package Capstone.Capstone.Controller.Calendar;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final MemberSpecService memberSpecService;
    private final CalendarService calendarService;
    @PostMapping("/member/calendar/saveProgress")
    public void saveProgress(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        Calendar calendar = calendarService.findDateRecord(memberSpec.getId(), LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
        if (calendar == null){
            calendarService.saveProgress(new Calendar(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth()));
        }
        //수정 더 하기
    }
    @Transactional
    @DeleteMapping("/member/calendar/deleteProgress")
    public void deleteProgress(int year, int month, int day){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        calendarService.deleteCalendarData(memberSpec.getId(),year, month, day);
    }
}
