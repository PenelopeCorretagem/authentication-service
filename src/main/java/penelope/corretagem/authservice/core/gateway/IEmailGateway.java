package penelope.corretagem.authservice.core.gateway;

public interface IEmailGateway {

    void sendPasswordResetEmail(String toEmail, String token);
}
