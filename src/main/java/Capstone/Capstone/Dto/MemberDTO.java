package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberDTO {
    private String realName;
    private String username;

    public MemberDTO(String realName, String username) {
        this.realName = realName;
        this.username = username;
    }
}
