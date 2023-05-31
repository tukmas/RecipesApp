package ru.live.recipesapp.services;

import ru.live.recipesapp.model.Ingredient;
import ru.live.recipesapp.model.Recipe;

public interface ValidationService {

    public boolean validate(Recipe recipe);

    public boolean validate(Ingredient ingredient);
}