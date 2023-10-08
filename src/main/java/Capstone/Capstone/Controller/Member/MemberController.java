package Capstone.Capstone.Controller.Member;

import Capstone.Capstone.Controller.Member.Form.CreateMemberForm;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String createForm(Model model){
        return "/members/signupUser";
    }

    @PostMapping("/signup")
    public String create(CreateMemberForm createMemberForm){
        Member member = new Member(createMemberForm.getRealName(), createMemberForm.getUsername(), passwordEncoder.encode(createMemberForm.getPassword()));
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "/members/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }

//    @GetMapping("/denied")
//    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Member member = (Member)authentication.getPrincipal();
//        model.addAttribute("username", member.getUsername());
//        model.addAttribute("exception", exception);
//
//        return "/user/login/denied";
//    }

    //    @GetMapping("/login")
//    public String loginForm(Model model){
//        model.addAttribute("loginMemberForm", new LoginMemberForm());
//        return "/members/loginMemberForm";
//    }
//
//    @PostMapping("/login")
//    public String login(@Valid LoginMemberForm loginMemberForm, BindingResult result) throws PasswordException, NotFoundIdException {
//        if(result.hasErrors()){
//            return "/members/loginMemberForm";
//        }
//        String username = loginMemberForm.getUsername();
//        Member member = memberService.findByUsername(username);
//        if(member != null){
//            memberService.login(member, loginMemberForm.getPassword());
//            ConnectedMember connectedMember = new ConnectedMember(member.getId());
//            connectedMemberService.connectId(connectedMember);
//            return "redirect:/mainPage";
//        }else {
//            return "redirect:/login?error"; // 지금은 경고 페이지이지만 에러메시지 띄울거임
//        }
//    }
//
//    @Transactional
//    @DeleteMapping("/logout")
//    public String logout(){
//        Member member = connectedMemberService.findByConnectedMember();
//        connectedMemberService.disconnectId(member.getId());
//        return "/lobbyPage";
//    }

}
