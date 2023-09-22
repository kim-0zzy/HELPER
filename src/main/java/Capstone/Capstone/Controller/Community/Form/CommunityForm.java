package Capstone.Capstone.Controller.Community.Form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityForm {

    private String ot_Username;
    private String ot_Password;
    private String title;
    private String content;
}
