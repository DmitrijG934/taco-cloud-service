package nn.dgordeev.taco.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder {

    private String name;
    private Delivery delivery;
    private Payment payment;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
