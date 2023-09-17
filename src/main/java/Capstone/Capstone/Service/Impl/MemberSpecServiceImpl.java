package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Entity.Routine;
import Capstone.Capstone.Repository.MemberRepository;
import Capstone.Capstone.Repository.MemberSpecRepository;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("MemberSpecService")
@Transactional
@RequiredArgsConstructor
public class MemberSpecServiceImpl implements MemberSpecService {

    private final MemberSpecRepository memberSpecRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long saveMemberSpec(MemberSpec memberSpec) {
        memberSpecRepository.save(memberSpec);
        return memberSpec.getId();
    }

    @Override
    public MemberSpec createMemberSpec(MemberSpec memberSpec) {
        Routine routine = createRoutine(memberSpec);
        memberSpec.setRoutine(routine);
        return memberSpec;
        // 컨트롤러에서는 MemberSpec을 프론트에서 받아온 정보를 토대로 생성자를 통해 생성 한 후
        // 해당 로직을 진행해 memberSpec을 영속화시킴.
        // memberSpec() -> createMemberSpec() -> saveMemberSpec(memberSpec)
    }

    @Override
    public void updateBasicMemberSpec(Long id, int height, int weight, int waist, int hip, int career, int age, int times, Gender gender, Goals goals) {
        MemberSpec memberSpec = memberSpecRepository.findByMemberId(id);
        memberSpec.setBasic(height, weight, waist, hip, career, age);
        memberSpec.setTimes(times);
        memberSpec.setGender(gender);
        memberSpec.setGoals(goals);
        memberSpec.setLevel(memberSpec.makeLevel());
        updateRoutine(memberSpec);
        // -> 컨트롤러에서 반환할떄 dto.getBasic, getTimes 등등 해줘야함
    }

    @Override
    public void updateRoutine(MemberSpec memberSpec) {
        Routine routine = createRoutine(memberSpec);
        memberSpec.setRoutine(routine);
    }

    @Override
    public MemberSpec findMemberSpecByMemberId(Long id) {
        return memberSpecRepository.findByMemberId(id);
    }

    @Override
    public MemberSpec findMemberSpec(Long id) {
        return memberSpecRepository.findById(id);
    }

    @Override
    public Routine createRoutine(MemberSpec memberSpec) {
        Routine routine = new Routine();
        routine.makePartition(memberSpec);
        routine.makeNutrition(memberSpec);
        return routine;
    }


}
