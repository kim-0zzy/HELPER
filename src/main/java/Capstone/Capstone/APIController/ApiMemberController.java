package Capstone.Capstone.APIController;

import Capstone.Capstone.Dto.MemberDTO;
import Capstone.Capstone.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    @GetMapping("/api/test/memberDto")
    public MemberDTO testMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return new MemberDTO(member.getRealName(), member.getUsername());
    }

    @GetMapping("/api/test/Security_memberDto")
    public MemberDTO testSecurityMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return new MemberDTO(member.getRealName(), member.getUsername());
    }

//    @Transactional
//    @DeleteMapping("/api/logout")
//    public void logout(){
//        Member member = connectedMemberService.findByConnectedMember();
//        connectedMemberService.disconnectId(member.getId());
//    }
}
