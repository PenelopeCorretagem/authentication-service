package penelope.corretagem.authservice.application.dto;

public record ValidateAccessTokenResponse(String email, Integer accessLevel) {
}
