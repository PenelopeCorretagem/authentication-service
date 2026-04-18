package penelope.corretagem.authservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateAccessTokenRequest(
    @NotBlank(message = "O token de acesso nao pode estar vazio")
    String token
) {
}
