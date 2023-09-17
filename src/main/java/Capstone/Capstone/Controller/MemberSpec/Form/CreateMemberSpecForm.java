package Capstone.Capstone.Controller.MemberSpec.Form;

import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CreateMemberSpecForm {
    private int height;
    private int weight;
    private int waist;
    private int hip;
    private int career;
    private int age;
    private int times;
    private String gender;
    private String goals;
}
