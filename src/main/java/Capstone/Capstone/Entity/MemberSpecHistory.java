package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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


    private LocalDateTime make_date_withTime;
    private LocalDate make_date;
    private int his_weight;
    private int his_career;

    public MemberSpecHistory(int his_weight, int his_career) {
        this.make_date = LocalDate.now();
        this.make_date_withTime = LocalDateTime.now();
        this.his_weight = his_weight;
        this.his_career = his_career;
    }

    public void setHistory(LocalDate make_date, LocalDateTime make_date_withTime, int his_career, int his_weight){
        this.make_date = LocalDate.now();
        this.make_date_withTime = LocalDateTime.now();
        this.his_weight = his_weight;
        this.his_career = his_career;
    }

    public void setMemberSpec(MemberSpec memberSpec) {
        this.memberSpec = memberSpec;
        memberSpec.getHistory().add(this);
    }

}
