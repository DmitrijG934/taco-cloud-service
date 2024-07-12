package nn.dgordeev.taco.repository;

import nn.dgordeev.taco.model.Taco;

public interface TacoRepository {

    Taco save(Taco taco);
    void save(Long orderId, Taco taco);
}
