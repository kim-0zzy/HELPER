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


    @GetMapping("/")
    public String lobby(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() == "anonymousUser"){
            return "/lobbyPage";
        }
        return "redirect:/mainPage";
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model){

        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        if(memberSpec == null){
            return "redirect:/recommend";
        }

        List<CalendarDTO> calendarDTOList = calendarService.findMonthlyRecord(memberSpec.getId(), LocalDate.now().getYear(),LocalDate.now().getMonthValue());
        model.addAttribute("calendarList", calendarDTOList);


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

    @GetMapping("/analyzing")
    public String analyzing(){
        return "/members/simple/Loading_ani";
    }


}
