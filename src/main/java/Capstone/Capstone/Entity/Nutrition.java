package Capstone.Capstone.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nutrition {
    private double protein;
    private double carbohydrate;
    private double fat;

    public Nutrition(double protein, double carbohydrate, double fat) {
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }
}
