package Capstone.Capstone.Controller.Member.Form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class CreateMemberForm {
    @NotBlank(message = "반드시 입력해주십시오.")
    private String realName;
    @NotBlank(message = "반드시 입력해주십시오.")
    private String username;
    @NotBlank(message = "반드시 입력해주십시오.")
    private String password;
}
