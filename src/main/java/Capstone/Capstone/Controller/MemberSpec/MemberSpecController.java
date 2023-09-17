package Capstone.Capstone.Controller.MemberSpec;

import Capstone.Capstone.Controller.MemberSpec.Form.CreateMemberSpecForm;
import Capstone.Capstone.Controller.MemberSpec.Form.UpdateMemberSpecForm;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Entity.MemberSpecHistory;
import Capstone.Capstone.Service.ConnectedMemberService;
import Capstone.Capstone.Service.HistoryService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberSpecController {
    private final MemberSpecService memberSpecService;
    private final ConnectedMemberService connectedMemberService;
    private final HistoryService historyService;

    @GetMapping("/member/my_spec")
    public String mySpec(){
        return "/members/mySpec";
    }

    @GetMapping("/member/inputMS")
    public String createMemberSpecForm(Model model){
        model.addAttribute("createMemberSpecForm", new CreateMemberSpecForm());
        return "/members/createMemberSpecForm";
    }

    @PostMapping("/member/inputMS")
    public String createMemberSpec(@Valid CreateMemberSpecForm createMemberSpecForm, BindingResult result) {
        if (result.hasErrors()) {
            return "/members/createMemberSpecForm";
        }
        int height = createMemberSpecForm.getHeight();
        int weight = createMemberSpecForm.getWeight();
        int waist = createMemberSpecForm.getWaist();
        int hip = createMemberSpecForm.getHip();
        int age = createMemberSpecForm.getAge();
        int career = createMemberSpecForm.getCareer() * 100;
        int times = createMemberSpecForm.getTimes();
        String formGender = createMemberSpecForm.getGender();
        Gender gender = null;
        String formGoal = createMemberSpecForm.getGoals();
        Goals goals = null;
        switch (formGender) {
            case "MALE" -> {
                gender = Gender.MALE;
            }
            case "FEMALE" ->{
                gender = Gender.FEMALE;
            }
        }
        switch (formGoal) {
            case "DIET" -> {
                goals = Goals.DIET;
            }case "BULKUP" ->{
                goals = Goals.BULKUP;
            }case "STRENGTH" ->{
                goals = Goals.STRENGTH;
            }case "ENDURANCE" ->{
                goals = Goals.ENDURE;
            }
        }
        Member member = connectedMemberService.findByConnectedMember();

        MemberSpec inputMemberSpec = new MemberSpec(member,height,weight,waist,hip,career,age,times,gender,goals);
        Level level = inputMemberSpec.makeLevel();
        inputMemberSpec.setLevel(level);

        MemberSpec memberSpec = memberSpecService.createMemberSpec(inputMemberSpec);
        Long id = memberSpecService.saveMemberSpec(memberSpec);

        return "redirect:/members/mySpec";
        // 수정할 거 있음
        // ex) 생성 후 루틴보러가기 메시지 띄우는거
    }

    @GetMapping("/member/updateMS")
    public String updateMemberSpecForm(Model model){
        model.addAttribute("updateMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/updateMemberSpecForm";
    }

    @PutMapping("/member/updateMS")
    public String updateMemberSpec(@Valid UpdateMemberSpecForm updateMemberSpecForm, BindingResult result){
        if (result.hasErrors()) {
            return "/members/updateMemberSpecForm";
        }
        Member member = connectedMemberService.findByConnectedMember();
        Long memberId = member.getId();
        Gender updateGender = null;
        Goals updateGoals = null;
        switch (updateMemberSpecForm.getGender()) {
            case "MALE" -> {
                updateGender = Gender.MALE;
            }
            case "FEMALE" ->{
                updateGender = Gender.FEMALE;
            }
        }
        switch (updateMemberSpecForm.getGoals()) {
            case "DIET" -> {
                updateGoals = Goals.DIET;
            }case "BULKUP" ->{
                updateGoals = Goals.BULKUP;
            }case "STRENGTH" ->{
                updateGoals = Goals.STRENGTH;
            }case "ENDURANCE" ->{
                updateGoals = Goals.ENDURE;
            }
        }
        memberSpecService.updateBasicMemberSpec(memberId,updateMemberSpecForm.getHeight(),
                updateMemberSpecForm.getWeight(),updateMemberSpecForm.getWaist(),updateMemberSpecForm.getHip(),
                updateMemberSpecForm.getCareer() * 100,updateMemberSpecForm.getAge(),updateMemberSpecForm.getTimes(),
                updateGender,updateGoals);
        MemberSpecHistory history = new MemberSpecHistory(updateMemberSpecForm.getWeight(), updateMemberSpecForm.getCareer());
        historyService.saveHistory(history);
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(memberId);
        memberSpec.makeLevel();
        Level level = memberSpec.getLevel();
        return "redirect:/members/mySpec";
    }

}
