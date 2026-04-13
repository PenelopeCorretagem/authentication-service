package penelope.corretagem.authservice.application.usecase.user;

import penelope.corretagem.authservice.core.exception.DomainValidationException;
import penelope.corretagem.authservice.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;

public class ResetPasswordUseCase {

    private final IUserRepository userRepository;
    private final IPasswordEncoderGateway passwordEncoder;

    public ResetPasswordUseCase(IUserRepository userRepository, IPasswordEncoderGateway passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String token, String newPassword) {
        var user = userRepository.findByPasswordResetToken(token)
            .orElseThrow(() -> new DomainValidationException("Token invalido ou nao encontrado."));

        user.applyNewPassword(passwordEncoder.encode(newPassword));
        userRepository.update(user);
    }
}
