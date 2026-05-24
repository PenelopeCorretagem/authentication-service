package penelope.corretagem.authservice.application.dto;

public record LoginResponse(String token, Long id, int accessLevelCode, String accessLevel, String accessLevelLabel) {
}
