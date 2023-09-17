package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainPartition {
    private String mainBack;
    private String mainChest;
    private String mainShoulder;
    private String mainLeg;
    private String mainReps;
    private String mainSets;
    private String mainAerobic;

    public MainPartition(String back, String chest, String shoulder, String leg, String reps, String sets, String aerobic) {
        this.mainBack = back;
        this.mainChest = chest;
        this.mainShoulder = shoulder;
        this.mainLeg = leg;
        this.mainReps = reps;
        this.mainSets = sets;
        this.mainAerobic = aerobic;
    }
}
