package Capstone.Capstone.Service;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Entity.Routine;

public interface MemberSpecService {
    public Long saveMemberSpec(MemberSpec memberSpec);
    public MemberSpec createMemberSpec(MemberSpec memberSpec);
    public void updateBasicMemberSpec(MemberSpec memberSpec, int height, int weight, int waist, int hip, int career, int age, int times, Gender gender, Goals goals);
    public Routine createRoutine(MemberSpec memberSpec);
    public void updateRoutine(MemberSpec memberSpec);
    public MemberSpec findMemberSpecByMemberId(Long id);
    public MemberSpec findMemberSpec(Long id);



}
