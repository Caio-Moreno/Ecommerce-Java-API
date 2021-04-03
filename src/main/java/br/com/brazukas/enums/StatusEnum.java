package br.com.brazukas.enums;

public enum  StatusEnum {
    A("A"),
    I("I");
    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
