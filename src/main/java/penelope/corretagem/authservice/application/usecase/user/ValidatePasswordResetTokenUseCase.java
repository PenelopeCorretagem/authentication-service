package penelope.corretagem.authservice.application.usecase.user;

import penelope.corretagem.authservice.core.exception.DomainValidationException;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;

import java.util.Date;

public class ValidatePasswordResetTokenUseCase {

    private final IUserRepository userRepository;

    public ValidatePasswordResetTokenUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String token) {
        var user = userRepository.findByPasswordResetToken(token)
            .orElseThrow(() -> new DomainValidationException("Token invalido ou nao encontrado."));

        if (user.getPasswordResetTokenExpiry() == null || user.getPasswordResetTokenExpiry().before(new Date())) {
            throw new DomainValidationException("Token expirado. Solicite um novo codigo.");
        }
    }
}
