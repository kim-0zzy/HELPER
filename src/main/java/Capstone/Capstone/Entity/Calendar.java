package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

    @Id
    @GeneratedValue
    @Column(name = "calendar_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memberSpec_id")
    private MemberSpec memberSpec;

    private int act_Year;
    private int act_Month;
    private int act_Day;
    private Boolean progress;

    public void setMemberSpec(MemberSpec memberSpec) {
        this.memberSpec = memberSpec;
        memberSpec.getCalendar().add(this);
    }
    public Calendar(int year, int month, int day) {
        this.act_Year = year;
        this.act_Month = month;
        this.act_Day = day;
        this.progress = Boolean.TRUE;
    }
}