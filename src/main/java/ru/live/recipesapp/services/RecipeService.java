package ru.live.recipesapp.services;

import ru.live.recipesapp.model.Recipe;

import java.util.Optional;
public interface RecipeService {

    Recipe save(Recipe recipe);

    Optional<Recipe> getById(Long id);
}
