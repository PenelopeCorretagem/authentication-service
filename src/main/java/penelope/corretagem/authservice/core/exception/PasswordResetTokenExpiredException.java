package penelope.corretagem.authservice.core.exception;

public class PasswordResetTokenExpiredException extends RuntimeException {

    public PasswordResetTokenExpiredException() {
        super("Token expirado ou invalido. Solicite uma nova redefinicao de senha.");
    }
}
