package nn.dgordeev.taco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TacoCloudException extends ResponseStatusException {

    public TacoCloudException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public TacoCloudException(String reason) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public TacoCloudException(String reason, Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, throwable);
    }
}
