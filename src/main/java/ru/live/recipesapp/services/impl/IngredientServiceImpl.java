package ru.live.recipesapp.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.live.recipesapp.exception.ValidationException;
import ru.live.recipesapp.model.Ingredient;
import ru.live.recipesapp.service.impl.FileService;
import ru.live.recipesapp.services.IngredientService;
import ru.live.recipesapp.services.ValidationService;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private static long idCounter = 1;
    private Map<Long, Ingredient> ingredients = new HashMap<>();
    private final ValidationService validationService;
    private final FileService fileService;

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;

    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    private Path ingredientPath;


    @Override
    public Ingredient save(Ingredient ingredient) {
        if (validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.put(idCounter++, ingredient);
        fileService.saveMapToFile(ingredients, ingredientPath);

        return ingredient;
    }

    @Override
    public Optional<Ingredient> getById(Long id) {
        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        if (validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.replace(id, ingredient);
        fileService.saveMapToFile(ingredients, ingredientPath);
        return ingredient;
    }

    @Override
    public Ingredient delete(Long id) {
        fileService.saveMapToFile(ingredients, ingredientPath);
        return ingredients.remove(id);
    }

    @Override
    public Map<Long, Ingredient> getAll() {
        return ingredients;
    }

    @PostConstruct
    private void inti() {
        ingredientPath = Path.of(ingredientsFilePath, ingredientsFileName);
        ingredients = fileService.readMapFromFile(ingredientPath, new TypeReference<HashMap<Long, Ingredient>>() {
        });

    }
}