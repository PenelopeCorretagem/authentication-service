package penelope.corretagem.authservice.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import penelope.corretagem.authservice.application.dto.ForgotPasswordRequest;
import penelope.corretagem.authservice.application.dto.LoginRequest;
import penelope.corretagem.authservice.application.dto.LoginResponse;
import penelope.corretagem.authservice.application.dto.ResetPasswordRequest;
import penelope.corretagem.authservice.application.dto.ValidateTokenRequest;
import penelope.corretagem.authservice.application.usecase.auth.AuthenticateUserUseCase;
import penelope.corretagem.authservice.application.usecase.user.GeneratePasswordResetTokenUseCase;
import penelope.corretagem.authservice.application.usecase.user.ResetPasswordUseCase;
import penelope.corretagem.authservice.application.usecase.user.ValidatePasswordResetTokenUseCase;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase;
    private final ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase,
                          GeneratePasswordResetTokenUseCase generatePasswordResetTokenUseCase,
                          ValidatePasswordResetTokenUseCase validatePasswordResetTokenUseCase,
                          ResetPasswordUseCase resetPasswordUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.generatePasswordResetTokenUseCase = generatePasswordResetTokenUseCase;
        this.validatePasswordResetTokenUseCase = validatePasswordResetTokenUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authenticateUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        generatePasswordResetTokenUseCase.execute(request.email());
        return ResponseEntity.ok(Map.of("message", "Se o e-mail estiver cadastrado, um codigo de verificacao sera enviado."));
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<Map<String, String>> validateToken(@Valid @RequestBody ValidateTokenRequest request) {
        validatePasswordResetTokenUseCase.execute(request.token());
        return ResponseEntity.ok(Map.of("message", "Token valido."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request.token(), request.newPassword());
        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso."));
    }
}
