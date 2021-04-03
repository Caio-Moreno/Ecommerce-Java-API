package br.com.brazukas.enums;

public enum  TipoUsuarioInternoEnum {
    ESTOQUISTA("ESTOQUISTA"),
    ADMIN("ADMIN"),
    CLIENTE("CLIENTE");

    private final String tipo;

    TipoUsuarioInternoEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
