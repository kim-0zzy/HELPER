package Capstone.Capstone.APIController;

import Capstone.Capstone.Dto.*;
import Capstone.Capstone.Entity.*;
import Capstone.Capstone.Service.HistoryService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberSpecController {

    private final MemberSpecService memberSpecService;
    private final HistoryService historyService;

    @GetMapping("/api/test/memberSpecDTO")
    public MemberSpecDTO testMSDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return RoutineDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .nutrition(memberSpec.getRoutine().getNutrition())
                .build();
    }

    @GetMapping("/api/test/partitionDTO")
    public PartitionDTO testPartitionDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return PartitionDTO.builder()
                .mainPartition(memberSpec.getRoutine().getMainPartition())
                .subPartition(memberSpec.getRoutine().getSubPartition())
                .build();
    }

    @GetMapping("/api/test/nutritionDTO")
    public NutritionDTO testNutritionDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        MemberSpec memberSpec = memberSpecService.findMemberSpecByMemberId(member.getId());
        return new NutritionDTO(memberSpec.getRoutine().getNutrition());
    }

    @GetMapping("/api/test/historyDTO")
    public List<MemberSpecHistoryDTO> testHistoryDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        List<MemberSpecHistoryDTO> historyDTOList = historyService.findAllHistory(member.getMemberSpec().getId());
        return historyDTOList;
    }
}
