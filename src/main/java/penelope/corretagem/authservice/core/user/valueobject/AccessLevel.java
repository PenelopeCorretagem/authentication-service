package penelope.corretagem.authservice.core.user.valueobject;

public enum AccessLevel {
    ADMINISTRADOR("Administrador"),
    CLIENTE("Cliente");

    private final String description;

    AccessLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
