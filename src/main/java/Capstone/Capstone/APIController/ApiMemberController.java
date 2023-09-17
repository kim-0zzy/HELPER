package Capstone.Capstone.APIController;

import Capstone.Capstone.Dto.MemberDTO;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Service.ConnectedMemberService;
import Capstone.Capstone.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final ConnectedMemberService connectedMemberService;

    @GetMapping("/api/test/memberDto")
    public MemberDTO testMember(){
        Member member = connectedMemberService.findByConnectedMember();
        return new MemberDTO(member.getRealName(), member.getUsername());
    }

    @Transactional
    @DeleteMapping("/api/logout")
    public void logout(){
        Member member = connectedMemberService.findByConnectedMember();
        connectedMemberService.disconnectId(member.getId());
    }
}
