package penelope.corretagem.authservice.infrastructure.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import penelope.corretagem.authservice.core.exception.IntegrationException;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenGatewayAdapter implements ITokenGateway {

    @Value("${app.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(String email, Long userId, int accessLevelCode) {
        try {
            Algorithm algorithm = buildAlgorithm();

            return JWT.create()
                .withIssuer("Penelope-API")
                .withSubject(email)
                .withClaim("userId", userId)
                .withClaim("accessLevel", accessLevelCode)
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
        } catch (JWTCreationException | IllegalArgumentException exception) {
            throw new IntegrationException("Erro ao gerar o token JWT", exception);
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            Algorithm algorithm = buildAlgorithm();

            return JWT.require(algorithm)
                .withIssuer("Penelope-API")
                .build()
                .verify(token)
                .getSubject();
        } catch (IntegrationException exception) {
            throw exception;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public int getAccessLevelFromToken(String token) {
        try {
            Algorithm algorithm = buildAlgorithm();
            var claim = JWT.require(algorithm)
                    .withIssuer("Penelope-API")
                    .build()
                    .verify(token)
                    .getClaim("accessLevel");

            Integer accessLevel = claim.asInt();

            if (accessLevel == null) {
                String accessLevelStr = claim.asString();
                if (accessLevelStr != null) {
                    try {
                        accessLevel = Integer.parseInt(accessLevelStr);
                    } catch (NumberFormatException e) {
                        throw new InvalidCredentialsException();
                    }
                }
            }

            if (accessLevel == null) {
                throw new InvalidCredentialsException();
            }

            return accessLevel;
        } catch (IntegrationException exception) {
            throw exception;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = buildAlgorithm();
            var claim = JWT.require(algorithm)
                    .withIssuer("Penelope-API")
                    .build()
                    .verify(token)
                    .getClaim("userId");

            Long userId = claim.asLong();
            if (userId == null) {
                Integer intUserId = claim.asInt();
                if (intUserId != null) {
                    userId = intUserId.longValue();
                }
            }

            if (userId == null) {
                throw new InvalidCredentialsException();
            }

            return userId;
        } catch (IntegrationException exception) {
            throw exception;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            throw new InvalidCredentialsException();
        }
    }

    private Algorithm buildAlgorithm() {
        if (secret == null || secret.isBlank()) {
            throw new IntegrationException("JWT_API_KEY nao configurada no auth-service");
        }
        return Algorithm.HMAC256(secret);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
