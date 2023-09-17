package Capstone.Capstone.APIController;

import Capstone.Capstone.Dto.*;
import Capstone.Capstone.Entity.*;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Service.ConnectedMemberService;
import Capstone.Capstone.Service.HistoryService;
import Capstone.Capstone.Service.MemberService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberSpecController {

    private final MemberSpecService memberSpecService;
    private final ConnectedMemberService connectedMemberService;
    private final HistoryService historyService;

    @GetMapping("/api/test/memberSpecDTO")
    public MemberSpecDTO testMSDTO(){
        Member member = connectedMemberService.findByConnectedMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return MemberSpecDTO.builder()
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
    }


    @GetMapping("/api/test/routineDTO")
    public RoutineDTO testRoutineDTO(){
        Member member = connectedMemberService.findByConnectedMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return RoutineDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .nutrition(memberSpec.getRoutine().getNutrition())
                .build();
    }

    @GetMapping("/api/test/partitionDTO")
    public PartitionDTO testPartitionDTO(){
        Member member = connectedMemberService.findByConnectedMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return PartitionDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .build();
    }

    @GetMapping("/api/test/nutritionDTO")
    public NutritionDTO testNutritionDTO(){
        Member member = connectedMemberService.findByConnectedMember();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return new NutritionDTO(memberSpec.getRoutine().getNutrition());
    }

    @GetMapping("/api/test/historyDTO")
    public List<MemberSpecHistoryDTO> testHistoryDTO(){
        Member member = connectedMemberService.findByConnectedMember();
        List<MemberSpecHistoryDTO> historyDTOList = historyService.findAllHistory(member.getMemberSpec().getId());
        return historyDTOList;
    }
}
