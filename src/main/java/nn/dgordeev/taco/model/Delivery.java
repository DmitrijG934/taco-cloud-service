package nn.dgordeev.taco.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Delivery {

    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "Street address is required")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Zip code is required")
    private String zip;
}
