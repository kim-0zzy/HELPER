package Capstone.Capstone.Controller.Calendar;

import Capstone.Capstone.Dto.CalendarDTO;
import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final MemberSpecService memberSpecService;
    private final CalendarService calendarService;

    private Long loadLoginMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return member.getId();
    }
    @GetMapping("/member/myCalendar")
    public String getCalendar(Model model){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        List<CalendarDTO> calendarDTOList = calendarService.findMonthlyRecord(memberSpec.getId(), LocalDate.now().getYear(),LocalDate.now().getMonthValue());
        int totalAmount = calendarDTOList.size();

        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("calendarList", calendarDTOList);
        return "/members/calendar";
    }

    @Transactional
    @PostMapping("/member/calendar/saveProgress")
    public void saveProgress(Model model) {
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        Calendar calendar = calendarService.findDateRecord(memberSpec.getId(), LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
        if (calendar == null) {
            calendarService.saveProgress(new Calendar(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth()));
            String SuccessMessage = "체크되었습니다.";
            model.addAttribute("message", SuccessMessage);
        }else {
            String FailMessage = "이미 존재하는 데이터입니다. 삭제하시겠습니까?";
            model.addAttribute("message", FailMessage);
            // 경고창 출력
        }
    }

    @Transactional
    @PostMapping("/member/calendar/saveProgress/day={day}")
    public String saveProgressWithDay(Model model, @PathVariable("day") int day) {
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        Calendar calendar = calendarService.findDateRecord(memberSpec.getId(), LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), day);
        if (calendar == null) {
            Calendar makeCalendar = calendarService.createCalendar(memberSpec, day);
            calendarService.saveProgress(makeCalendar);
            String SuccessMessage = "success";
            model.addAttribute("message", SuccessMessage);
        }else {
            String FailMessage = "fail";
            model.addAttribute("message", FailMessage);
            // 경고창 출력
        }
        return "redirect:/mainPage";
    }

    @Transactional
    @DeleteMapping("/member/calendar/deleteProgress/day={day}")
    public String deleteProgress(@PathVariable("day") int day){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        calendarService.deleteCalendarData(memberSpec.getId(),LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), day);
        return "redirect:/mainPage";
    }
}
