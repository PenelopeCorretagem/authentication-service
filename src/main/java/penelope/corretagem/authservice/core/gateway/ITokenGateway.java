package penelope.corretagem.authservice.core.gateway;

public interface ITokenGateway {

    String generateToken(String email, String accessLevel);

    String getEmailFromToken(String token);

    String getAccessLevelFromToken(String token);
}
