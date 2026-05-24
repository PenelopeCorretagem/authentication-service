package penelope.corretagem.authservice.core.user.valueobject;

public enum AccessLevel {
    ADMINISTRADOR(1, "Administrador", "administrador"),
    CLIENTE(2, "Cliente", "cliente"),
    CORRETOR(3, "Corretor", "corretor");

    private final int code;
    private final String description;
    private final String externalValue;

    AccessLevel(int code, String description, String externalValue) {
        this.code = code;
        this.description = description;
        this.externalValue = externalValue;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
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

    public String toExternalValue() {
        return externalValue;
    }
}
