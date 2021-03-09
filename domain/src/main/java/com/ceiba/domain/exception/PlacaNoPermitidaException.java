package com.ceiba.domain.exception;

public class PlacaNoPermitidaException extends RuntimeException {

    public static final String PLACA_NO_PERMITIDA_MSJ = "No está autorizado a ingresar";

    public PlacaNoPermitidaException() {
        super(PLACA_NO_PERMITIDA_MSJ);
    }
}
