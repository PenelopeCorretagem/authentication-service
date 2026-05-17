package penelope.corretagem.authservice.application.usecase.auth;

import penelope.corretagem.authservice.application.dto.ValidateAccessTokenResponse;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;

public class ValidateAccessTokenUseCase {

    private final ITokenGateway tokenGateway;

    public ValidateAccessTokenUseCase(ITokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public ValidateAccessTokenResponse execute(String token) {
        String email = tokenGateway.getEmailFromToken(token);
        int accessLevel = tokenGateway.getAccessLevelFromToken(token);

        return new ValidateAccessTokenResponse(email, accessLevel);
    }
}
