package penelope.corretagem.authservice.application.dto;

public record ValidateAccessTokenResponse(String email, Long id, int accessLevelCode, String accessLevel, String accessLevelLabel) {
}
