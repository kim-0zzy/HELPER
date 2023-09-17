package Capstone.Capstone.Controller.Member;

import Capstone.Capstone.Controller.Member.Form.CreateMemberForm;
import Capstone.Capstone.Controller.Member.Form.LoginMemberForm;
import Capstone.Capstone.Entity.ConnectedMember;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Exception.NotFoundIdException;
import Capstone.Capstone.Exception.PasswordException;
import Capstone.Capstone.Service.ConnectedMemberService;
import Capstone.Capstone.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ConnectedMemberService connectedMemberService;

    @GetMapping("/")
    public String lobby(){
        return "/lobbyPage";
    }

    @GetMapping("/mainPage")
    public String mainPage(){
        return "/members/mainPage";
    }

    @GetMapping("/testPage")
    public String testPage(){
        return "/testPage";
    }

    @GetMapping("/signup")
    public String createForm(Model model){
        model.addAttribute("createMemberForm", new CreateMemberForm());
        return "/members/createMemberForm";
    }

    @PostMapping("/signup")
    public String create(@Valid CreateMemberForm createMemberForm, BindingResult result){
        if(result.hasErrors()){
            return "/members/createMemberForm";
        }
        String realName = createMemberForm.getRealName();
        String username = createMemberForm.getUsername();
        String password = createMemberForm.getPassword();
        Member member = new Member(realName,username,password);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginMemberForm", new LoginMemberForm());
        return "/members/loginMemberForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginMemberForm loginMemberForm, BindingResult result) throws PasswordException, NotFoundIdException {
        if(result.hasErrors()){
            return "/members/loginMemberForm";
        }
        String username = loginMemberForm.getUsername();
        Optional<Member> member = memberService.findByUsername(username);
        if(member.isPresent()){
            memberService.login(member.get(), loginMemberForm.getPassword());
            ConnectedMember connectedMember = new ConnectedMember(member.get().getId());
            connectedMemberService.connectId(connectedMember);
            return "redirect:/mainPage";
        }else {
            return "redirect:/login?error"; // 지금은 경고 페이지이지만 에러메시지 띄울거임
        }
    }

    @Transactional
    @DeleteMapping("/logout")
    public String logout(){
        Member member = connectedMemberService.findByConnectedMember();
        connectedMemberService.disconnectId(member.getId());
        return "/lobbyPage";
    }
}
