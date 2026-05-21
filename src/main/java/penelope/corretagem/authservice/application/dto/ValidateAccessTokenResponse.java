package penelope.corretagem.authservice.application.dto;

public record ValidateAccessTokenResponse(String email, int accessLevel, String accessLevelLabel) {
}
