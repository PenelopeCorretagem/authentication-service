package penelope.corretagem.authservice.application.usecase.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import penelope.corretagem.authservice.application.dto.LoginRequest;
import penelope.corretagem.authservice.application.dto.LoginResponse;
import penelope.corretagem.authservice.core.exception.InvalidCredentialsException;
import penelope.corretagem.authservice.core.gateway.IPasswordEncoderGateway;
import penelope.corretagem.authservice.core.gateway.ITokenGateway;
import penelope.corretagem.authservice.core.user.User;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;
import penelope.corretagem.authservice.core.user.valueobject.AccessLevel;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPasswordEncoderGateway passwordEncoderGateway;

    @Mock
    private ITokenGateway tokenGateway;

    @InjectMocks
    private AuthenticateUserUseCase useCase;

    @Test
    void shouldAuthenticateUser_whenCredentialsAreValid() {
        // Given
        LoginRequest request = new LoginRequest("admin@penelope.com", "123456");
        User user = User.restore(
            1L,
            "Admin",
            "admin@penelope.com",
            "$2a$hashed",
            null,
            null,
            null,
            null,
            null,
            AccessLevel.ADMINISTRADOR,
            LocalDate.now(),
            true,
            null,
            null
        );

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoderGateway.matches(request.password(), user.getPassword())).thenReturn(true);

        // When
        LoginResponse response = useCase.execute(request);

        // Then
        assertNull(response.token());
        assertEquals(1L, response.id());
        assertEquals("ADMINISTRADOR", response.accessLevel().toUpperCase(Locale.ROOT));
    }

    @Test
    void shouldThrowInvalidCredentials_whenUserIsNotFound() {
        // Given
        LoginRequest request = new LoginRequest("missing@penelope.com", "123456");
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(request));
    }

    @Test
    void shouldThrowInvalidCredentials_whenPasswordDoesNotMatch() {
        // Given
        LoginRequest request = new LoginRequest("admin@penelope.com", "wrong-password");
        User user = User.restore(
            1L,
            "Admin",
            "admin@penelope.com",
            "$2a$hashed",
            null,
            null,
            null,
            null,
            null,
            AccessLevel.ADMINISTRADOR,
            LocalDate.now(),
            true,
            null,
            null
        );

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoderGateway.matches(request.password(), user.getPassword())).thenReturn(false);

        // When / Then
        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(request));
    }
}



