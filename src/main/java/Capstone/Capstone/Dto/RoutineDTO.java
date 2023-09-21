package Capstone.Capstone.Dto;

import Capstone.Capstone.Entity.MainPartition;
import Capstone.Capstone.Entity.Nutrition;
import Capstone.Capstone.Entity.SubPartition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter @Setter
@Builder
public class RoutineDTO {
    @Embedded
    private MainPartition mainPartition;
    @Embedded
    private SubPartition subPartition;
    @Embedded
    private Nutrition nutrition;

    public RoutineDTO(MainPartition mainPartition, SubPartition subPartition, Nutrition nutrition) {
        this.mainPartition = mainPartition;
        this.subPartition = subPartition;
        this.nutrition = nutrition;
    }
}
