package Capstone.Capstone.Controller.Community;

import Capstone.Capstone.Controller.Community.Form.CommunityForm;
import Capstone.Capstone.Entity.Community;
import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String getCommunityPage(){
        return "/community";
    }

    @GetMapping("/community/saveNotice")
    public String getSaveNoticePage(Model model){
        model.addAttribute("CommunityForm", new CommunityForm());
        return "/community/createNewNotice";
    }

    @PostMapping("/community/saveNotice")
    public String SaveNotice(@Valid CommunityForm communityForm, BindingResult result){
        if(result.hasErrors()){
            return "/community/createNewNotice";
        }
        String ot_username = communityForm.getOt_Username();
        String ot_password = communityForm.getOt_Password();
        String title = communityForm.getTitle();
        String content = communityForm.getContent();
        Community community = new Community(ot_username,ot_password,title,content, LocalDateTime.now());
        communityService.saveNotice(community);
        return "redirect:/community";

    }
}