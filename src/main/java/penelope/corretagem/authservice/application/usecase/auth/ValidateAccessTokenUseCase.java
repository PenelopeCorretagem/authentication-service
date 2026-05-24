package penelope.corretagem.authservice.application.usecase.auth;

import penelope.corretagem.authservice.application.dto.ValidateAccessTokenResponse;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;
import penelope.corretagem.authservice.core.user.valueobject.AccessLevel;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;

public class ValidateAccessTokenUseCase {

    private final ITokenGateway tokenGateway;
    private final IUserRepository userRepository;

    public ValidateAccessTokenUseCase(ITokenGateway tokenGateway, IUserRepository userRepository) {
        this.tokenGateway = tokenGateway;
        this.userRepository = userRepository;
    }

    public ValidateAccessTokenResponse execute(String token) {
        String email = tokenGateway.getEmailFromToken(token);
        int accessLevel = tokenGateway.getAccessLevelFromToken(token);
        try {
            AccessLevel level = AccessLevel.fromCode(accessLevel);
            Long userId = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new)
                .getId();
            return new ValidateAccessTokenResponse(email, userId, level.toExternalValue());
        } catch (IllegalArgumentException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
