package Capstone.Capstone.Controller.Community;

import Capstone.Capstone.Controller.Community.Form.CommunityForm;
import Capstone.Capstone.Controller.Community.Form.DeleteCommunityForm;
import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;
import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String getCommunityPage(Model model){
        List<CommunityDTO> allNotice = communityService.findAllNotice();
        model.addAttribute("allNotice", allNotice);

        return "/community/community_home";
    }

    @GetMapping("/community/createNotice")
    public String getCreateNoticePage(Model model){
        model.addAttribute("CommunityForm", new CommunityForm());
        return "/community/createNewNotice";
    }

    @PostMapping("/community/createNotice")
    public String createNotice(@Valid CommunityForm communityForm, BindingResult result){
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

    @GetMapping("/community/{title}/{id}") // 페이지에서 타임리프 리스트로 객체 뿌려준 후 클릭 시 id, title 반환해 api 동작하는 방식.
    public String getNotice(@PathVariable("title") String title,
                            @PathVariable("id") Long id,
                            Model model){
        CommunityDTO communityDTO = communityService.findByIdWithTitle(id, title);
        model.addAttribute("content", communityDTO);
        return null;
    }

    @GetMapping("/community/askAgain")
    public String askAgain(Model model, Long id){
        model.addAttribute("deleteCommunityForm", new DeleteCommunityForm());
        model.addAttribute("noticeId",id);
        return "/community/askAgain";
    }

    @Transactional
    @DeleteMapping("/community/deleteNotice")
    public String deleteNotice(DeleteCommunityForm deleteCommunityForm){
        Long id = deleteCommunityForm.getId();
        String ot_Password = deleteCommunityForm.getOt_password();
        communityService.deleteNotice(id, ot_Password);
        // 삭제되었습니다 (확인) 경고창 출력
        return "redirect:/community";
    }
}