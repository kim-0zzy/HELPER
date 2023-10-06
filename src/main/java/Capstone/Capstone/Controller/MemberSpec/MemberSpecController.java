package Capstone.Capstone.Controller.MemberSpec;

import Capstone.Capstone.Controller.MemberSpec.Form.CreateMemberSpecForm;
import Capstone.Capstone.Controller.MemberSpec.Form.UpdateMemberSpecForm;
import Capstone.Capstone.Dto.NutritionDTO;
import Capstone.Capstone.Dto.PartitionDTO;
import Capstone.Capstone.Dto.RoutineDTO;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Entity.MemberSpecHistory;
import Capstone.Capstone.Service.HistoryService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final HistoryService historyService;

    @GetMapping("/member/my_spec")
    public String mySpec(){
        return "/members/mySpec";
    }

    @GetMapping("/member/recommend")
    public String checkRecommend(){
        return "/members/recommendCheck";
    }

    @GetMapping("/member/analyze_complete")
    public String analyzeComplete(){
        return "/members/analyze_complete";
    }

    @GetMapping("/member/inputMS")
    public String createMemberSpecForm(Model model){
        model.addAttribute("createMemberSpecForm", new CreateMemberSpecForm());
        return "/members/memberSpec/createMemberSpecForm";
    }

    @PostMapping("/member/inputMS")
    public String createMemberSpec(@Valid CreateMemberSpecForm createMemberSpecForm, BindingResult result) {
        if (result.hasErrors()) {
            return "/members/memberSpec/createMemberSpecForm";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();

        MemberSpec inputMemberSpec = new MemberSpec(member,height,weight,waist,hip,career,age,times,gender,goals);
        Level level = inputMemberSpec.makeLevel();
        inputMemberSpec.setLevel(level);

        MemberSpec memberSpec = memberSpecService.createMemberSpec(inputMemberSpec);
        Long id = memberSpecService.saveMemberSpec(memberSpec);

        return "redirect:/member/analyze_complete";
        // 수정할 거 있음
        // ex) 생성 후 루틴보러가기 메시지 띄우는거
    }

    @GetMapping("/member/updateMS")
    public String updateMemberSpecForm(Model model){
        model.addAttribute("updateMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/memberSpec/updateMemberSpecForm";
    }

    @PutMapping("/member/updateMS")
    public String updateMemberSpec(@Valid UpdateMemberSpecForm updateMemberSpecForm, BindingResult result){
        if (result.hasErrors()) {
            return "/members/memberSpec/updateMemberSpecForm";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
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

    @GetMapping("/member/myRoutine")
    public String getMyRoutine(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();

        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());

        PartitionDTO partitionDTO = PartitionDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .build();

        model.addAttribute(partitionDTO);
        return "/members/memberSpec/myRoutine";
    }

    @GetMapping("/member/myNutrition")
    public String getMyNutrition(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());

        model.addAttribute(new NutritionDTO(memberSpec.getRoutine().getNutrition()));
        return "/members/memberSpec/myNutrition";
    }
}

