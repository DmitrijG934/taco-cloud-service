package nn.dgordeev.taco.controller;

import lombok.extern.slf4j.Slf4j;
import nn.dgordeev.taco.model.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "order";
    }

    @PostMapping
    public String processOrder(TacoOrder tacoOrder, SessionStatus status) {
        log.info("Order submitted: {}", tacoOrder);
        status.setComplete();
        return "redirect:/";
    }
}
