package Capstone.Capstone.Controller.Home;

import Capstone.Capstone.Dto.CalendarDTO;
import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.CommunityService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberSpecService memberSpecService;
    private final CalendarService calendarService;
    private final CommunityService communityService;

    private Long loadLoginMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return member.getId();
    }

    @GetMapping("/testPage")
    public String testPage(){
        return "/testPage";
    }

    @GetMapping("/")
    public String lobby(){
        return "/lobbyPage";
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model){
        // 세개 서비스 모두 사용해서 꾸며야함
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        if(memberSpec == null){
            return "redirect:/recommend";
        }
        // 캘린더 리스트 객체
        List<CalendarDTO> calendarDTOList = calendarService.findMonthlyRecord(memberSpec.getId(), LocalDate.now().getYear(),LocalDate.now().getMonthValue());
        model.addAttribute("calendarList", calendarDTOList);

        // 커뮤니티 리스트 객체
        List<CommunityDTO> communityDTOList = communityService.findRecently5();
        model.addAttribute("recently5Notice", communityDTOList);

        return "/members/mainPage";
    }

    @GetMapping("/recommend")
    public String checkRecommend(){
        return "/members/simple/recommendCheck";
    }

    @GetMapping("/analyzeComplete")
    public String analyzeComplete(){
        return "/members/simple/analyze_complete";
    }


}
