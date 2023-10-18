package Capstone.Capstone.Controller.Community;

import Capstone.Capstone.Controller.Community.Form.CommunityForm;
import Capstone.Capstone.Controller.Community.Form.DeleteCommunityForm;
import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;
import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community/page={pageNum}")
    public String getCommunityPage(Model model, @PathVariable("pageNum") int pageNum){
        // 커뮤니티 페이지 클릭 시 페이지 번호 0 전달
        Page<Community> page = communityService.findAllNotice(pageNum - 1);
        List<Community> content = page.getContent();

        List<CommunityDTO> allNotice = new ArrayList<>();
        for (Community community : content) {
            CommunityDTO communityDTO = CommunityDTO.builder()
                    .id(community.getId())
                    .ot_Username(community.getOt_Username())
                    .title(community.getTitle())
                    .content(community.getContent())
                    .createDate(community.getCreateDate())
                    .build();
            allNotice.add(communityDTO);
        }
        long totalContent = page.getTotalElements();
        int nowPage = page.getPageable().getPageNumber() + 1;
        int startPage = 1;
        int endPage = page.getTotalPages();

        model.addAttribute("pageNum", (long)pageNum);
        model.addAttribute("allNotice", allNotice);
        model.addAttribute("totalContent", totalContent);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);

//        return "/community/community_home";
        return "/community/community";
    }

//    @GetMapping("/community/pageNum/searchOnTitle")
//    public String searchOnTitle(Model model,
//                                @RequestParam("pageNum") int pageNum,
//                                @RequestParam("title") String title){
//        Page<Community> page = communityService.findByTitle(title, pageNum);
//        List<Community> content = page.getContent();
//        long totalContent = page.getTotalElements();
//
//        List<CommunityDTO> searchOnTitleList = new ArrayList<>();
//
//        for (Community community : content) {
//            CommunityDTO communityDTO = CommunityDTO.builder()
//                    .id(community.getId())
//                    .ot_Username(community.getOt_Username())
//                    .title(community.getTitle())
//                    .content(community.getContent())
//                    .createDate(community.getCreateDate())
//                    .build();
//            searchOnTitleList.add(communityDTO);
//        }
//        model.addAttribute("pageNum", (long)pageNum);
//        model.addAttribute("searchOnTitleList", searchOnTitleList);
//        model.addAttribute("totalContent", totalContent);
//        return "/community/searchOnTitle";
//    }


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
        Community community = new Community(ot_username,ot_password,title,content, LocalDate.now(), LocalDateTime.now());
        communityService.saveNotice(community);
        return "redirect:/community";
    }

    @GetMapping("/community/detail")
    public String getNotice(@RequestParam("id") Long id,
                            Model model){
        CommunityDTO communityDTO = communityService.findById(id);
        model.addAttribute("content", communityDTO);
        return "/community/community_detail";
    }

    @GetMapping("/community/askAgain")
    public String askAgain(Model model, Long id){
        model.addAttribute("deleteCommunityForm", new DeleteCommunityForm());
        model.addAttribute("noticeId", id);
        return "/community/askAgain";
    }

    @Transactional
    @DeleteMapping("/community/deleteNotice")
    public String deleteNotice(DeleteCommunityForm deleteCommunityForm){
        Long id = deleteCommunityForm.getId(); // ${id}
        String ot_Password = deleteCommunityForm.getOt_password();
        communityService.deleteNotice(id, ot_Password);
        // 삭제되었습니다 (확인) 경고창 출력
        return "redirect:/community";
    }
}