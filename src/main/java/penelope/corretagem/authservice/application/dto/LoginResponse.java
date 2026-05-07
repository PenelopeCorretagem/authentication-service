package penelope.corretagem.authservice.application.dto;

public record LoginResponse(String token, Long id, Integer accessLevel) {
}
