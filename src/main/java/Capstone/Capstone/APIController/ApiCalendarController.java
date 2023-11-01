package Capstone.Capstone.APIController;

import Capstone.Capstone.Dto.CalendarDTO;
import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiCalendarController {

    private final MemberSpecService memberSpecService;
    private final CalendarService calendarService;
    @PostMapping("/api/member/saveTodayProgress")
    public void saveTodayProgress(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        Calendar calendar = calendarService.findDateRecord(memberSpec.getId()
                , LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
        if (calendar == null){
            Calendar makeCalendar = calendarService.createCalendar_today(memberSpec);
            calendarService.saveProgress(makeCalendar);
        }
    }
    @GetMapping("/api/member/progressList")
    public List<CalendarDTO> getProgressList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        List<CalendarDTO> calendarDTOList = calendarService.findAllRecord(memberSpec.getId());
        return calendarDTOList;
    }

    @DeleteMapping("/api/member/deleteTodayProgress")
    public void deleteTodayProgress(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        Calendar calendar = calendarService.findDateRecord(memberSpec.getId()
                , LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
        if (calendar != null){
            calendarService.deleteCalendarData(memberSpec.getId(),LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
        }
    }

}
