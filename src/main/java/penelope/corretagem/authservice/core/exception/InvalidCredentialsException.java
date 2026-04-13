package penelope.corretagem.authservice.core.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Usuario ou senha invalidos");
    }
}
