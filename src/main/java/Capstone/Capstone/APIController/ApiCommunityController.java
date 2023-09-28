package Capstone.Capstone.APIController;
import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ApiCommunityController {

    private final CommunityService communityService;

    @GetMapping("/api/community/loadAllNotice")
    public List<CommunityDTO> getAllNotice(){
        return communityService.findAllNotice();
    }

    @GetMapping("/api/community/loadNoticeByTitle")
    public List<CommunityDTO> getNoticeByTitle(String title) {
        return communityService.findByTitle(title);
    }

}
