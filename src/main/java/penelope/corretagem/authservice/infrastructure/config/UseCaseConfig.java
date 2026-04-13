package penelope.corretagem.authservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import penelope.corretagem.authservice.application.usecase.auth.AuthenticateUserUseCase;
import penelope.corretagem.authservice.application.usecase.user.GeneratePasswordResetTokenUseCase;
import penelope.corretagem.authservice.application.usecase.user.ResetPasswordUseCase;
import penelope.corretagem.authservice.application.usecase.user.ValidatePasswordResetTokenUseCase;
import penelope.corretagem.authservice.core.gateway.IEmailGateway;
import penelope.corretagem.authservice.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(IUserRepository userRepository,
                                                           IPasswordEncoderGateway passwordEncoderGateway,
                                                           ITokenGateway tokenGateway) {
        return new AuthenticateUserUseCase(userRepository, passwordEncoderGateway, tokenGateway);
    }

    @Bean
    public GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase(IUserRepository userRepository,
                                                                                IEmailGateway emailGateway) {
        return new GeneratePasswordResetTokenUseCase(userRepository, emailGateway);
    }

    @Bean
    public ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase(IUserRepository userRepository) {
        return new ValidatePasswordResetTokenUseCase(userRepository);
    }

    @Bean
    public ResetPasswordUseCase resetPasswordUseCase(IUserRepository userRepository,
                                                     IPasswordEncoderGateway passwordEncoderGateway) {
        return new ResetPasswordUseCase(userRepository, passwordEncoderGateway);
    }
}
