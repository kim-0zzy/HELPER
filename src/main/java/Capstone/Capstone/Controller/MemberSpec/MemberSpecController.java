package Capstone.Capstone.Controller.MemberSpec;

import Capstone.Capstone.Controller.MemberSpec.Form.CreateMemberSpecForm;
import Capstone.Capstone.Controller.MemberSpec.Form.UpdateMemberSpecForm;
import Capstone.Capstone.Dto.*;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberSpecController {
    private final MemberSpecService memberSpecService;
    private final HistoryService historyService;


    private Long loadLoginMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        return member.getId();
    }

    @GetMapping("/member/myPage")
    public String mySpec(Model model){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        MemberSpecDTO memberSpecDTO =  MemberSpecDTO.builder()
                .height(memberSpec.getHeight())
                .weight(memberSpec.getWeight())
                .waist(memberSpec.getWaist())
                .hip(memberSpec.getHip())
                .career(memberSpec.getCareer() / 100)
                .age(memberSpec.getAge())
                .times(memberSpec.getTimes())
                .gender(memberSpec.getGender())
                .goals(memberSpec.getGoals())
                .level(memberSpec.getLevel())
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberDTO memberDTO = MemberDTO.builder()
                .realName(member.getRealName())
                .username(member.getUsername())
                .build();

        MemberSpecHistoryDTO memberSpecHistoryDTO = historyService.findFirstRecord(memberSpec.getId());

        double muscleNervous = 0;
        // 근신경계 활성도
        if(memberSpecDTO.getCareer() > 30) {
            muscleNervous = 100;
        }else{
            muscleNervous = memberSpecDTO.getCareer() * 3.3;
        }
        // 근육증가량
        double muscleMass = (memberSpecDTO.getCareer() - memberSpecHistoryDTO.getHis_career()) * 0.3 ;
        // 함께한 시간
        int togetherCareer = memberSpecDTO.getCareer() - memberSpecHistoryDTO.getHis_career();

        model.addAttribute("nickName", memberDTO.getRealName());
        model.addAttribute("togetherCareer", togetherCareer);
        model.addAttribute("memberSpec",memberSpecDTO);
        model.addAttribute("muscleNervous", muscleNervous);
        model.addAttribute("muscleMass", muscleMass);
        return "/members/memberSpec/myPage";
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
            case "1" -> {
                gender = Gender.MALE;
            }
            case "2" ->{
                gender = Gender.FEMALE;
            }
        }
        switch (formGoal) {
            case "1" -> {
                goals = Goals.DIET;
            }case "2" ->{
                goals = Goals.BULKUP;
            }case "3" ->{
                goals = Goals.STRENGTH;
            }case "4" ->{
                goals = Goals.ENDURE;
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();

        MemberSpec inputMemberSpec = new MemberSpec(member,height,weight,waist,hip,career,age,times,gender,goals);
        Level level = inputMemberSpec.makeLevel();
        inputMemberSpec.setLevel(level);

        MemberSpecHistory history = new MemberSpecHistory(weight, career);
        history.setMemberSpec(inputMemberSpec);
        historyService.saveHistory(history);

        MemberSpec memberSpec = memberSpecService.createMemberSpec(inputMemberSpec);
        Long id = memberSpecService.saveMemberSpec(memberSpec);

        return "redirect:/analyzeComplete";
        // 수정할 거 있음
        // ex) 생성 후 루틴보러가기 메시지 띄우는거
    }

    @GetMapping("/member/updateMS")
    public String updateMemberSpecForm(Model model){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        MemberSpecDTO memberSpecDTO =  MemberSpecDTO.builder()
                .height(memberSpec.getHeight())
                .weight(memberSpec.getWeight())
                .waist(memberSpec.getWaist())
                .hip(memberSpec.getHip())
                .career(memberSpec.getCareer() / 100)
                .age(memberSpec.getAge())
                .times(memberSpec.getTimes())
                .gender(memberSpec.getGender())
                .goals(memberSpec.getGoals())
                .level(memberSpec.getLevel())
                .build();

        model.addAttribute("savedMemberSpec", memberSpecDTO);
        model.addAttribute("updateMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/memberSpec/updateMemberSpecForm";
    }

    @Transactional
    @PostMapping("/member/updateMS")
    public String updateMemberSpec(@Valid UpdateMemberSpecForm updateMemberSpecForm, BindingResult result
            ,@RequestParam("weight") int weight, @RequestParam("waist") int waist
            ,@RequestParam("hip") int hip, @RequestParam("age") int age
            ,@RequestParam("times") int times, @RequestParam("goals") String goals
                ){
        if (result.hasErrors()) {
            return "/members/memberSpec/updateMemberSpecForm";
        }
        Long memberId = loadLoginMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(memberId);
        MemberSpecDTO memberSpecDTO =  MemberSpecDTO.builder()
                .height(memberSpec.getHeight())
                .weight(memberSpec.getWeight())
                .waist(memberSpec.getWaist())
                .hip(memberSpec.getHip())
                .career(memberSpec.getCareer() / 100)
                .age(memberSpec.getAge())
                .times(memberSpec.getTimes())
                .gender(memberSpec.getGender())
                .goals(memberSpec.getGoals())
                .level(memberSpec.getLevel())
                .build();

        Goals updateGoals = null;
        switch (goals) {
            case "1" -> {
                updateGoals = Goals.DIET;
            }case "2" ->{
                updateGoals = Goals.BULKUP;
            }case "3" ->{
                updateGoals = Goals.STRENGTH;
            }case "4" ->{
                updateGoals = Goals.ENDURE;
            }
        }

        memberSpecService.updateBasicMemberSpec(memberSpec,memberSpecDTO.getHeight(),
                weight,waist,hip, memberSpecDTO.getCareer() * 100, age,times,
                memberSpecDTO.getGender(),updateGoals);

        MemberSpecHistory history = new MemberSpecHistory(weight, memberSpecDTO.getCareer() * 100);
        history.setMemberSpec(memberSpec);
        historyService.saveHistory(history);


        memberSpec.makeLevel();
        Level level = memberSpec.getLevel();
        System.out.println("level = " + level);
        return "redirect:/member/myPage";
    }

    @GetMapping("/member/reEnterMS")
    public String reEnterMemberSpecForm(Model model){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        MemberSpecDTO memberSpecDTO =  MemberSpecDTO.builder()
                .height(memberSpec.getHeight())
                .weight(memberSpec.getWeight())
                .waist(memberSpec.getWaist())
                .hip(memberSpec.getHip())
                .career(memberSpec.getCareer() / 100)
                .age(memberSpec.getAge())
                .times(memberSpec.getTimes())
                .gender(memberSpec.getGender())
                .goals(memberSpec.getGoals())
                .level(memberSpec.getLevel())
                .build();

        model.addAttribute("savedMemberSpec", memberSpecDTO);
        model.addAttribute("reEnterMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/memberSpec/updateMemberSpecForm";
    }
    @Transactional
    @PostMapping("/member/reEnterMS")
    public String reEnterMemberSpec(@Valid UpdateMemberSpecForm reEnterMemberSpecForm, BindingResult result
            ,@RequestParam("height") int height, @RequestParam("weight") int weight, @RequestParam("waist") int waist
            ,@RequestParam("hip") int hip, @RequestParam("age") int age, @RequestParam("career") int career
            ,@RequestParam("times") int times, @RequestParam("gender") String gender, @RequestParam("goals") String goals){

        if (result.hasErrors()) {
            return "/members/memberSpec/updateMemberSpecForm_";
        }
        Long memberId = loadLoginMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());

        Gender updateGender = null;
        Goals updateGoals = null;
        switch (reEnterMemberSpecForm.getGender()) {
            case "MALE" -> {
                updateGender = Gender.MALE;
            }
            case "FEMALE" ->{
                updateGender = Gender.FEMALE;
            }
        }
        switch (reEnterMemberSpecForm.getGoals()) {
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

        memberSpecService.updateBasicMemberSpec(memberSpec,height,
                weight,waist,hip, career * 100, age,times,
                updateGender,updateGoals);

        MemberSpecHistory firstRecord = historyService.findFirstRecord_V2(memberId);
        firstRecord.setHistory(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                reEnterMemberSpecForm.getCareer() * 100, reEnterMemberSpecForm.getWeight());
        Long historyId = firstRecord.getId();

        memberSpec.makeLevel();
        Level level = memberSpec.getLevel();
        return "redirect:/analyzeComplete";
    }

    @GetMapping("/member/myRoutine")
    public String getMyRoutine(Model model){

        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());

        int times = memberSpec.getTimes();
        PartitionDTO partitionDTO = PartitionDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .build();

        model.addAttribute("times", times);
        model.addAttribute("partition",partitionDTO);
        return "/members/memberSpec/myRoutine";
    }

    @GetMapping("/member/myNutrition")
    public String getMyNutrition(Model model){

        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        MemberSpecDTO memberSpecDTO =  MemberSpecDTO.builder()
                .height(memberSpec.getHeight())
                .weight(memberSpec.getWeight())
                .waist(memberSpec.getWaist())
                .hip(memberSpec.getHip())
                .career(memberSpec.getCareer() / 100)
                .age(memberSpec.getAge())
                .times(memberSpec.getTimes())
                .gender(memberSpec.getGender())
                .goals(memberSpec.getGoals())
                .level(memberSpec.getLevel())
                .build();

        double BMR = memberSpec.getRoutine().getBMR(memberSpec);

        String calculation_Kcal = "";
        switch (memberSpecDTO.getGoals()){
            case DIET -> calculation_Kcal = "-500";
            case BULKUP,STRENGTH -> calculation_Kcal = "+200";
            case ENDURE -> calculation_Kcal = "-+0";
        }

        model.addAttribute("memberSpecDTO", memberSpecDTO);
        model.addAttribute("WHR", (double) memberSpecDTO.getWaist() / (double) memberSpecDTO.getHip());
        model.addAttribute("BMR", BMR);
        model.addAttribute("goal", memberSpecDTO.getGoals());
        model.addAttribute("calculation_Kcal", calculation_Kcal);
        model.addAttribute("nutrition",new NutritionDTO(memberSpec.getRoutine().getNutrition()));
        return "/members/memberSpec/myNutrition";
    }

    @GetMapping("/member/myHistory")
    public String getMyHistory(Model model){
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(loadLoginMember());
        List<MemberSpecHistoryDTO> historyDTOList = historyService.findAllHistory(memberSpec.getId());

        model.addAttribute("HistoryList", historyDTOList);
        return "/members/memberSpec/myHistory";
    }
}

