package ru.live.recipesapp.services;

import ru.live.recipesapp.model.Ingredient;
import ru.live.recipesapp.model.Recipe;

import java.util.Map;
import java.util.Optional;
public interface IngredientService {

        Ingredient save(Ingredient ingredient);

        Optional<Ingredient> getById(Long id);

        Ingredient update(Long id, Ingredient ingredient);

        Ingredient delete(Long id);

        Map<Long, Ingredient> getAll();
}

