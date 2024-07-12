package nn.dgordeev.taco.repository;

import nn.dgordeev.taco.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder tacoOrder);
}
