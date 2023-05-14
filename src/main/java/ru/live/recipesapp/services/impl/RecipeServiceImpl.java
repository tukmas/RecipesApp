package ru.live.recipesapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.live.recipesapp.exception.ValidationException;
import ru.live.recipesapp.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.live.recipesapp.services.RecipeService;
import ru.live.recipesapp.service.impl.FileService;
import ru.live.recipesapp.services.ValidationService;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private static long idCounter = 1;
    private Map<Long, Recipe> recipes = new HashMap<>();
    private final ValidationService validationService;
    private final FileService fileService;

    @Value("${path.to.recipes.file}")
    private String recipesFilePath;

    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    private Path recipesPath;

    @Override
    public Recipe save(Recipe recipe) {
        if (validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(idCounter++, recipe);
        fileService.saveMapToFile(recipes, recipesPath);
        return recipe;
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        if (validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        fileService.saveMapToFile(recipes, recipesPath);
        return recipes.replace(id, recipe);
    }

    @Override
    public Recipe delete(Long id) {
        fileService.saveMapToFile(recipes, recipesPath);
        return recipes.remove(id);
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return recipes;
    }

    @PostConstruct
    private void inti() {
        recipesPath = Path.of(recipesFilePath, recipesFileName);
        recipes = fileService.readMapFromFile(recipesPath, new TypeReference<HashMap<Long, Recipe>>() {
        });

    }
}