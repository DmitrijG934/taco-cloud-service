package nn.dgordeev.taco.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nn.dgordeev.taco.model.TacoOrder;
import nn.dgordeev.taco.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    public OrderController(
            @Autowired(required = false) ValidationErrorsLogService errorsLogService,
            OrderRepository orderRepository
    ) {
        this.errorsLogService = errorsLogService;
        this.orderRepository = orderRepository;
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
        var savedOrder = orderRepository.save(tacoOrder);
        log.info("Order submitted & saved: {}", savedOrder);
        status.setComplete();
        return "redirect:/";
    }
}
