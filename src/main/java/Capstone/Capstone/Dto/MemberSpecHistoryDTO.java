package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberSpecHistoryDTO {
    private int make_Year;
    private int make_Month;
    private int make_Day;
    private int his_weight;
    private int his_career;

    public MemberSpecHistoryDTO(int make_Year, int make_Month, int make_Day, int his_weight, int his_career) {
        this.make_Year = make_Year;
        this.make_Month = make_Month;
        this.make_Day = make_Day;
        this.his_weight = his_weight;
        this.his_career = his_career;
    }
}
