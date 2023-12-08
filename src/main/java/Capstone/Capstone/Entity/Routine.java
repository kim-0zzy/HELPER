package Capstone.Capstone.Entity;

import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Getter
public class Routine {

    @Id @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @OneToOne(mappedBy = "routine")
    private MemberSpec memberSpec;
    @Embedded
    private MainPartition mainPartition;
    @Embedded
    private SubPartition subPartition;
    @Embedded
    private Nutrition nutrition;

    public void setMemberSpec(MemberSpec memberSpec) {
        this.memberSpec = memberSpec;
    }
    public void setMainPartition(MainPartition mainPartition) {
        this.mainPartition = mainPartition;
    }
    public void setSubPartition(SubPartition subpartition){
        this.subPartition = subpartition;
    }
    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }
    public double getBMR(MemberSpec memberSpec){
        int height = memberSpec.getHeight();
        int weight = memberSpec.getWeight();
        int age = memberSpec.getAge();

        return switch (memberSpec.getGender()) {
            case MALE -> ((88.4 + (13.4 * weight)) + (4.8 * height) - (5.68 * age));
            case FEMALE -> ((447.6 + (9.25 * weight)) + (3.1 * height) - (4.33 * age));
        };
    }
    public void makePartition(MemberSpec memberSpec){
        Goals goals = memberSpec.getGoals();
        Level level = memberSpec.getLevel();
        String aerobic = switch (goals) {
            case DIET -> "20min";
            case ENDURE, BULKUP, STRENGTH -> "None";
        };

        SetMap setMap = new SetMap();
        RoutineMap routineMap = new RoutineMap();

        String sets = setMap.makeSets(memberSpec);
        String reps = setMap.makeReps(memberSpec);

        String main = "Main";
        String basic = "Basic";
        String sub = "Sub";
        String none = "None";
        String back = null, chest = null, shoulder = null, leg = null;
        String subBack = null, subChest = null, subShoulder = null, subLeg = null;

        switch (level) {
            case UNTRAINED -> {
                back = routineMap.getBack(basic);
                chest = routineMap.getChest(basic);
                shoulder = routineMap.getShoulder(basic);
                leg = routineMap.getLeg(basic);
                subBack = routineMap.getBack(none);
                subChest = routineMap.getChest(none);
                subShoulder = routineMap.getShoulder(none);
                subLeg = routineMap.getLeg(none);
            }
            case BEGINNER -> {
                back = routineMap.getBack(main);
                chest = routineMap.getChest(main);
                shoulder = routineMap.getShoulder(main);
                leg = routineMap.getLeg(main);
                subBack = routineMap.getBack(none);
                subChest = routineMap.getChest(none);
                subShoulder = routineMap.getShoulder(none);
                subLeg = routineMap.getLeg(none);
            }
            case NOVICE -> {
                back = routineMap.getBack(main);
                chest = routineMap.getChest(main);
                shoulder = routineMap.getShoulder(main);
                leg = routineMap.getLeg(main);
                subBack = routineMap.getBack(sub);
                subChest = routineMap.getChest(sub);
                subShoulder = routineMap.getShoulder(sub);
                subLeg = routineMap.getLeg(sub);
            }
        }
        MainPartition mainPartition = new MainPartition(back, chest, shoulder, leg , reps, sets, aerobic);
        SubPartition subPartition = new SubPartition(subBack, subChest, subShoulder, subLeg, reps, sets);
        this.setMainPartition(mainPartition);
        this.setSubPartition(subPartition);
    }
    public void makeNutrition(MemberSpec memberSpec){
        // 공식 수정 예정 and Service 계층으로 양도 예정
        double protein = Math.round(memberSpec.getWeight() * 1.6);
        double carbohydrate = Math.round((this.getBMR(memberSpec) * 0.4) / 4);
        double fat = Math.round(memberSpec.getWeight() * 0.8);
        Nutrition nutrition = new Nutrition(protein, carbohydrate, fat);
        this.setNutrition(nutrition);
    }
    public static class RoutineMap{
        Map<String, String > back = new ConcurrentHashMap<>();
        Map<String, String > chest = new ConcurrentHashMap<>();
        Map<String, String > shoulder = new ConcurrentHashMap<>();
        Map<String, String > leg = new ConcurrentHashMap<>();

        public RoutineMap(){
            back.put("Main", "PullUp");
            back.put("Basic", "LatPullDown");
            back.put("Sub", "SeatedRow");
            back.put("None", "Unnecessary");

            chest.put("Main", "BenchPress");
            chest.put("Basic", "ChestPress");
            chest.put("Sub", "InclineBenchPress");
            chest.put("None", "Unnecessary");

            shoulder.put("Main", "OverHeadPress");
            shoulder.put("Basic", "ShoulderPressMachine");
            shoulder.put("Sub", "SideLateralRaise");
            shoulder.put("None", "Unnecessary");

            leg.put("Main", "Squat|DeadLift");
            leg.put("Basic", "LegCull&Extension");
            leg.put("Sub", "LegPress");
            leg.put("None", "Unnecessary");
        }
        public String getBack(String grade){
            return back.get(grade);
        }
        public String getChest(String grade){
            return chest.get(grade);
        }
        public String getShoulder(String grade){
            return shoulder.get(grade);
        }
        public String getLeg(String grade){
            return leg.get(grade);
        }

    }
    public static class SetMap{
        String reps, sets;
        public String makeReps(MemberSpec memberSpec){
            Goals goals = memberSpec.getGoals();
            switch (goals){
                case STRENGTH:
                    reps = "3~5";
                    break;
                case ENDURE:
                    reps = "15~20";
                    break;
                case DIET, BULKUP:
                    reps = "8~12";
                    break;
            }
            return reps;
        }
        public String makeSets(MemberSpec memberSpec){
            int times = memberSpec.getTimes();
            switch (times){
                case 2, 3:
                    sets = "5";
                    break;
                case 4:
                    sets = "4";
                    break;
            }
            return sets;
        }
    }
}
