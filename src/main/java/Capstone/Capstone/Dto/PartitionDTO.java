package Capstone.Capstone.Dto;

import Capstone.Capstone.Entity.MainPartition;
import Capstone.Capstone.Entity.SubPartition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter @Setter
@Builder
public class PartitionDTO {
    @Embedded
    private MainPartition mainPartition;
    @Embedded
    private SubPartition subPartition;

    public PartitionDTO(MainPartition mainPartition, SubPartition subPartition) {
        this.mainPartition = mainPartition;
        this.subPartition = subPartition;
    }

}
