package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubPartition {
    private String subBack;
    private String subChest;
    private String subShoulder;
    private String subLeg;
    private String subReps;
    private String subSets;

    public SubPartition(String subBack, String subChest, String subShoulder, String subLeg, String subReps, String subSets) {
        this.subBack = subBack;
        this.subChest = subChest;
        this.subShoulder = subShoulder;
        this.subLeg = subLeg;
        this.subReps = subReps;
        this.subSets = subSets;
    }
}
