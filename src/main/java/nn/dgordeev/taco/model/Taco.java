package nn.dgordeev.taco.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;
    private Date createdAt = new Date();

    @NotBlank(message = "Taco name is required")
    @Size(min = 5, message = "Name should contain at least 5 characters")
    private String name;
    @NotNull
    @Size(min = 2, message = "At least 2 ingredients")
    private List<Ingredient> ingredients;
}
