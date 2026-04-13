package penelope.corretagem.authservice.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import penelope.corretagem.authservice.core.exception.DomainValidationException;
import penelope.corretagem.authservice.core.exception.IntegrationException;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.exception.PasswordResetTokenExpiredException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<StandardErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new StandardErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler({DomainValidationException.class, PasswordResetTokenExpiredException.class})
    public ResponseEntity<StandardErrorResponse> handleValidation(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new StandardErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<StandardErrorResponse> handleIntegration(IntegrationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
            .body(new StandardErrorResponse(HttpStatus.BAD_GATEWAY.value(), ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponse> handleGeneric(Exception ex) {

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StandardErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocorreu um erro interno no servidor.",
                        LocalDateTime.now()
                ));
    }
}
