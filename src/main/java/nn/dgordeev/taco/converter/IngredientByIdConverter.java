package nn.dgordeev.taco.converter;

import lombok.RequiredArgsConstructor;
import nn.dgordeev.taco.model.Ingredient;
import nn.dgordeev.taco.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.getById(id);
    }

}
