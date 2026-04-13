package penelope.corretagem.authservice.application.usecase.auth;

import penelope.corretagem.authservice.application.dto.LoginRequest;
import penelope.corretagem.authservice.application.dto.LoginResponse;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;

public class AuthenticateUserUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoderGateway;
    private final ITokenGateway tokenGateway;

    public AuthenticateUserUseCase(IUserRepository userRepository,
                                   IPasswordEncoderGateway passwordEncoderGateway,
                                   ITokenGateway tokenGateway) {
        this.userRepository = userRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.tokenGateway = tokenGateway;
    }

    public LoginResponse execute(LoginRequest request) {
        var user = userRepository.findByEmail(request.email())
            .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoderGateway.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String role = user.getAccessLevel().name();
        String token = tokenGateway.generateToken(user.getEmail(), role);

        return new LoginResponse(token, user.getId(), user.getAccessLevel().toString());
    }
}
