package penelope.corretagem.authservice.application.usecase.auth;

import penelope.corretagem.authservice.application.dto.ValidateAccessTokenResponse;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.user.valueobject.AccessLevel;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;

public class ValidateAccessTokenUseCase {

    private final ITokenGateway tokenGateway;

    public ValidateAccessTokenUseCase(ITokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public ValidateAccessTokenResponse execute(String token) {
        String email = tokenGateway.getEmailFromToken(token);
        int accessLevel = tokenGateway.getAccessLevelFromToken(token);
        Long userId = tokenGateway.getUserIdFromToken(token);
        try {
            AccessLevel level = AccessLevel.fromCode(accessLevel);
            return new ValidateAccessTokenResponse(email, userId, accessLevel, level.toExternalValue(), level.getDescription());
        } catch (IllegalArgumentException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
