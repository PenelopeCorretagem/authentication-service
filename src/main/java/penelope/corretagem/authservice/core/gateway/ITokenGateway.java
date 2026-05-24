package penelope.corretagem.authservice.core.gateway;

public interface ITokenGateway {

    String generateToken(String email, Long userId, int accessLevelCode);

    String getEmailFromToken(String token);

    int getAccessLevelFromToken(String token);

    Long getUserIdFromToken(String token);
}
