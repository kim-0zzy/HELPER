package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class CommunityDTO {

    private Long id;
    private String ot_Username;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public CommunityDTO(Long id, String ot_Username, String title, String content, LocalDateTime createDate) {
        this.id = id;
        this.ot_Username = ot_Username;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
}
