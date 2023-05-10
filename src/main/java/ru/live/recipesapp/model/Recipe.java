package ru.live.recipesapp.model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    protected List<Ingredient> ingredientList;
    private List<String> steps;

}
