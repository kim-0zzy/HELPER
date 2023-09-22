package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class CommunityDTO {

    private String ot_Username;
    private String ot_Password;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public CommunityDTO(String ot_Username, String ot_Password, String title, String content, LocalDateTime createDate) {
        this.ot_Username = ot_Username;
        this.ot_Password = ot_Password;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
}
