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


//    https://velog.io/@nyong_i/List%EB%A5%BC-Dto%EB%A1%9C-%EB%B0%98%ED%99%98%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-RESTful-API
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
            Calendar makeCalendar = calendarService.createCalendar(memberSpec);
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
//    @PostMapping("/api/member/saveDetailProgress/{year}_{month}_{day}")
//    public void saveDetailProgress(@PathVariable int day, @PathVariable int month, @PathVariable int year){
//        Member member = connectedMemberService.findByConnectedMember();
//        Calendar calendar = calendarService.findDateRecord(member.getMemberSpec().getId(), year, month, day);
//        if (calendar == null){
//            calendarService.saveProgress(new Calendar(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth()));
//        } //수정 하기
//
//    }


}
