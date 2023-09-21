package Capstone.Capstone.Dto;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Routine;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class MemberSpecDTO {
    private int height;
    private int weight;
    private int waist;
    private int hip;
    private int career;
    private int age;
    private int times;
    private Gender gender;
    private Goals goals;
    private Level level;

    public MemberSpecDTO(int height, int weight, int waist, int hip, int career, int age, int times, Gender gender, Goals goals, Level level) {
        this.height = height;
        this.weight = weight;
        this.waist = waist;
        this.hip = hip;
        this.career = career;
        this.age = age;
        this.times = times;
        this.gender = gender;
        this.goals = goals;
        this.level = level;
    }
}
