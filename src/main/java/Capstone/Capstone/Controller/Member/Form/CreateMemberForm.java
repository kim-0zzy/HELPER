package Capstone.Capstone.Controller.Member.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CreateMemberForm {
    @NotBlank(message = "반드시 입력해주십시오.")
    private String realName;
    @NotBlank(message = "반드시 입력해주십시오.")
    private String username;
    @NotBlank(message = "반드시 입력해주십시오.")
    private String password;
}
