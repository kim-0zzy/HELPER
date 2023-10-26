package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class MemberSpecHistoryDTO {
//    private int make_Year;
//    private int make_Month;
//    private int make_Day;
    private LocalDate make_date;
    private LocalDateTime make_date_withTime;
    private int his_weight;
    private int his_career;

    public MemberSpecHistoryDTO(LocalDate make_date, LocalDateTime make_date_withTime, int his_weight, int his_career) {
        this.make_date = LocalDate.now();
        this.make_date_withTime = LocalDateTime.now();
        this.his_weight = his_weight;
        this.his_career = his_career;
    }
}
