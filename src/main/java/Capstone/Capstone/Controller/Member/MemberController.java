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
import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long loadLoginMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return member.getId();
    }

    @GetMapping("/signup")
    public String createForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() == "anonymousUser"){
            return "/members/signupUser";
        }
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String create(CreateMemberForm createMemberForm) throws IllegalStateException{
        Member existMember = memberService.findByUsername(createMemberForm.getUsername());
        if(existMember == null){
            try{
                Member member = new Member(createMemberForm.getRealName(), createMemberForm.getUsername(), passwordEncoder.encode(createMemberForm.getPassword()));
                memberService.join(member);
            }catch (IllegalStateException e){
                return "redirect:/signup";
            }
        }else{
            return "redirect:/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() == "anonymousUser"){
            return "/members/login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        response.sendRedirect("/");
    }
}
