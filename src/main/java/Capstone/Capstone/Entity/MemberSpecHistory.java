package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSpecHistory {

    @Id @GeneratedValue
    @Column(name = "histories_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memberSpec_id")
    private MemberSpec memberSpec;

    private int make_Year;
    private int make_Month;
    private int make_Day;
    private int his_weight;
    private int his_career;

    public MemberSpecHistory(int his_weight, int his_career) {
        this.make_Year = LocalDate.now().getYear();
        this.make_Month = LocalDate.now().getMonth().getValue();
        this.make_Day = LocalDate.now().getDayOfMonth();
        this.his_weight = his_weight;
        this.his_career = his_career;
    }

    public void setHistory(int make_Year, int make_Month, int make_Day, int his_career, int his_weight){
        this.make_Year = LocalDate.now().getYear();
        this.make_Month = LocalDate.now().getMonth().getValue();
        this.make_Day = LocalDate.now().getDayOfMonth();
        this.his_weight = his_weight;
        this.his_career = his_career;
    }

    public void setMemberSpec(MemberSpec memberSpec) {
        this.memberSpec = memberSpec;
        memberSpec.getHistory().add(this);
    }

}
