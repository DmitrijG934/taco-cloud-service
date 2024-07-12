package nn.dgordeev.taco.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nn.dgordeev.taco.model.Ingredient;
import nn.dgordeev.taco.model.Taco;
import nn.dgordeev.taco.model.TacoOrder;
import nn.dgordeev.taco.repository.IngredientRepository;
import nn.dgordeev.taco.service.ValidationErrorsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final ValidationErrorsLogService errorsLogService;

    public DesignTacoController(
            IngredientRepository ingredientRepository,
            @Autowired(required = false) ValidationErrorsLogService errorsLogService
    ) {
        this.ingredientRepository = ingredientRepository;
        this.errorsLogService = errorsLogService;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder tacoOrder
    ) {
        if (errors.hasErrors()) {
            if (Objects.nonNull(errorsLogService)) {
                this.errorsLogService.logValidationErrors(errors);
            }
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Process Taco request {}", taco);
        return "redirect:/orders/current";
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Arrays.asList(Ingredient.Type.values()).forEach(type ->
                model.addAttribute(
                        type.name().toLowerCase(),
                        filterByType(ingredientRepository.findAll(), type)
                ));
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(ingredient -> ingredient.type().equals(type))
                .toList();
    }

}
