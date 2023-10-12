package Capstone.Capstone.Controller.MemberSpec;

import Capstone.Capstone.Controller.MemberSpec.Form.CreateMemberSpecForm;
import Capstone.Capstone.Controller.MemberSpec.Form.UpdateMemberSpecForm;
import Capstone.Capstone.Dto.MemberSpecDTO;
import Capstone.Capstone.Dto.MemberSpecHistoryDTO;
import Capstone.Capstone.Dto.NutritionDTO;
import Capstone.Capstone.Dto.PartitionDTO;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Entity.MemberSpecHistory;
import Capstone.Capstone.Service.HistoryService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

        MemberSpecHistoryDTO memberSpecHistoryDTO = historyService.findFirstRecord(memberSpec.getId());

        int beforeCareer = memberSpecDTO.getCareer() - memberSpecHistoryDTO.getHis_career();
        int beforeWeight = memberSpecDTO.getWeight() - memberSpecHistoryDTO.getHis_weight();
        double muscleNervous = 0;
        // 근신경계

        if(memberSpecDTO.getCareer() > 30) {
            muscleNervous = 100;
        }else{
            muscleNervous = memberSpecDTO.getCareer() * 3.3;
        }

        // 근육량
        double muscleMass = (memberSpecDTO.getCareer() - memberSpecHistoryDTO.getHis_career()) * 0.3 ;


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
        model.addAttribute("updateMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/memberSpec/updateMemberSpecForm";
    }

    @Transactional
    @PutMapping("/member/updateMS")
    public String updateMemberSpec(@Valid UpdateMemberSpecForm updateMemberSpecForm, BindingResult result){
        if (result.hasErrors()) {
            return "/members/memberSpec/updateMemberSpecForm";
        }
        Long memberId = loadLoginMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(memberId);

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

        MemberSpecHistory history = new MemberSpecHistory(updateMemberSpecForm.getWeight(), updateMemberSpecForm.getCareer() * 100);
        history.setMemberSpec(memberSpec);
        historyService.saveHistory(history);


        memberSpec.makeLevel();
        Level level = memberSpec.getLevel();
        return "redirect:/members/mySpec";
    }

    @GetMapping("/member/reEnterMS")
    public String reEnterMemberSpecForm(Model model){
        model.addAttribute("reEnterMemberSpecForm", new UpdateMemberSpecForm());
        return "/members/memberSpec/updateMemberSpecForm";
    }
    @Transactional
    @PutMapping("/member/reEnterMS")
    public String reEnterMemberSpec(@Valid UpdateMemberSpecForm reEnterMemberSpecForm, BindingResult result){
        if (result.hasErrors()) {
            return "/members/memberSpec/updateMemberSpecForm";
        }
        Long memberId = loadLoginMember();

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
        memberSpecService.updateBasicMemberSpec(memberId,reEnterMemberSpecForm.getHeight(),
                reEnterMemberSpecForm.getWeight(),reEnterMemberSpecForm.getWaist(),reEnterMemberSpecForm.getHip(),
                reEnterMemberSpecForm.getCareer() * 100,reEnterMemberSpecForm.getAge(),reEnterMemberSpecForm.getTimes(),
                updateGender,updateGoals);

        MemberSpecHistory firstRecord = historyService.findFirstRecord_V2(memberId);
        firstRecord.setHistory(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                reEnterMemberSpecForm.getCareer() * 100, reEnterMemberSpecForm.getWeight());
        Long historyId = firstRecord.getId();

        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(memberId);
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

