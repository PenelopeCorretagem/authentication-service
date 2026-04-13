package penelope.corretagem.authservice.application.dto;

public record ResetPasswordRequest(String token, String newPassword) {
}
