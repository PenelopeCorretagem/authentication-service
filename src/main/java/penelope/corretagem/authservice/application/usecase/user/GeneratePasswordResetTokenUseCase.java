package penelope.corretagem.authservice.application.usecase.user;

import penelope.corretagem.authservice.core.gateway.IEmailGateway;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;

import java.security.SecureRandom;
import java.util.Date;

public class GeneratePasswordResetTokenUseCase {

    private final IUserRepository userRepository;
    private final IEmailGateway emailGateway;
    private final SecureRandom secureRandom = new SecureRandom();

    public GeneratePasswordResetTokenUseCase(IUserRepository userRepository, IEmailGateway emailGateway) {
        this.userRepository = userRepository;
        this.emailGateway = emailGateway;
    }

    public void execute(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = String.format("%06d", secureRandom.nextInt(1_000_000));
            Date expiryDate = new Date(System.currentTimeMillis() + 3_600_000);

            user.generatePasswordResetToken(token, expiryDate);
            userRepository.update(user);
            emailGateway.sendPasswordResetEmail(user.getEmail(), token);
        });
    }
}
