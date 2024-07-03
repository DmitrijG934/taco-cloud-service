package nn.dgordeev.taco.repository;

import nn.dgordeev.taco.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientRepository {

    private static final List<Ingredient> ingredients = List.of(
            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
            new Ingredient("LETC", "Letucce", Ingredient.Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
    );

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient getById(String id) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find a proper ingredient with id %s".formatted(id)));
    }
}
