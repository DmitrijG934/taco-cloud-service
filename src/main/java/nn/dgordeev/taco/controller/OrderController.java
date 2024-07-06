package nn.dgordeev.taco.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nn.dgordeev.taco.model.TacoOrder;
import nn.dgordeev.taco.service.ValidationErrorsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final ValidationErrorsLogService errorsLogService;

    public OrderController(@Autowired(required = false) ValidationErrorsLogService errorsLogService) {
        this.errorsLogService = errorsLogService;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "order";
    }

    @PostMapping
    public String processOrder(
            @Valid TacoOrder tacoOrder,
            Errors errors,
            SessionStatus status
    ) {
        if (errors.hasErrors()) {
            if (Objects.nonNull(errorsLogService)) {
                this.errorsLogService.logValidationErrors(errors);
            }
            return "order";
        }
        log.info("Order submitted: {}", tacoOrder);
        status.setComplete();
        return "redirect:/";
    }
}
