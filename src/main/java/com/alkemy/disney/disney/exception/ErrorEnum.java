package com.alkemy.disney.disney.exception;

public enum ErrorEnum {
    IDPELINOTVALID("ID movie not valid"),
    IDPERSONAJENOTVALID("ID character not valid"),
    USERALREADYEXIST("Username already exists"),
    USERORPASSWORDNOTFOUND("Username or password not found"),
    TRYINGTOSENDMAILFAIL("Error trying to send the email"),
    MALFORMED_JSON("Entered data type error"),
    PARAMNOTFOUND("Param not found");

    private String mensaje;

    ErrorEnum(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
