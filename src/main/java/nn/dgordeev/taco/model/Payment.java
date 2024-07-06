package nn.dgordeev.taco.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
public class Payment {

    @CreditCardNumber(message = "Credit card number is not valid")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;
}
