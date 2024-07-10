package nn.dgordeev.taco.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder {

    private Long id;
    private Date createdAt;

    @NotNull
    @Valid
    private Delivery delivery;
    @NotNull
    @Valid
    private Payment payment;
    @NotEmpty(message = "At least 1 taco in the order")
    private List<Taco> tacos;

    public TacoOrder() {
        this.tacos = new ArrayList<>();
    }

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
