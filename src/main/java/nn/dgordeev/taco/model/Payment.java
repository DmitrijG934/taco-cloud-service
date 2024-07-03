package nn.dgordeev.taco.model;

import lombok.Data;

@Data
public class Payment {

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
}
