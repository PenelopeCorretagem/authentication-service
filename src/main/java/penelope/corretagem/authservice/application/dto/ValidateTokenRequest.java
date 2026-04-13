package penelope.corretagem.authservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidateTokenRequest(
    @NotBlank(message = "O token de redefinicao nao pode estar vazio")
    String token
) {
}
