package Capstone.Capstone.Dto;

import Capstone.Capstone.Entity.Nutrition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter @Setter
@Builder
public class NutritionDTO {
    @Embedded
    private Nutrition nutrition;

    public NutritionDTO(Nutrition nutrition) {
        this.nutrition = nutrition;
    }
}
