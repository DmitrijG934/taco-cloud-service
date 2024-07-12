package nn.dgordeev.taco.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Slf4j
@Service
@ConditionalOnProperty(
        prefix = "taco-cloud",
        name = "validation-logging",
        havingValue = "true"
)
public class ValidationErrorsLogService {

    public void logValidationErrors(Errors errors) {
        var fieldErrors = errors.getFieldErrors();
        log.warn("Validation errors occurred: size = {}", fieldErrors.size());
        fieldErrors
                .forEach(err -> log.warn("Field -> {}, Rejected value -> {}",
                        err.getField(), err.getRejectedValue()));
    }
}
