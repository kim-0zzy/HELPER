package Capstone.Capstone.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CalendarDTO {
    private int act_Year;
    private int act_Month;
    private int act_Day;
    private Boolean progress;

    public CalendarDTO(int act_Year, int act_Month, int act_Day, Boolean progress) {
        this.act_Year = act_Year;
        this.act_Month = act_Month;
        this.act_Day = act_Day;
        this.progress = progress;
    }
}
