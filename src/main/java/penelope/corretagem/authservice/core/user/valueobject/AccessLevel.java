package penelope.corretagem.authservice.core.user.valueobject;

public enum AccessLevel {
    ADMINISTRADOR(1, "Administrador"),
    CLIENTE(2, "Cliente");

    private final int code;
    private final String description;

    AccessLevel(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return description;
    }

    public static AccessLevel fromCode(int code) {
        for (AccessLevel value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Codigo de nivel de acesso invalido: " + code);
    }
}
