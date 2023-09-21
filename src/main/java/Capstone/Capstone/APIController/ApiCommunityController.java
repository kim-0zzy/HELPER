package Capstone.Capstone.APIController;

import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiCommunityController {

    private final CommunityService communityService;

    @PostMapping("/api/community/saveNotice")
    public void saveNotice(){

    }

    @GetMapping("/api/community/allNotice")
    public void getAllNotice(){

    }

    @GetMapping("/api/community/noticeByTitle")
    public void getNoticeByTitle(){

    }
}
