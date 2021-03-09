package com.ceiba.domain.exception;

public class SinCupoException extends RuntimeException {

    public static final String SIN_CUPO_MSJ = "No hay cupo disponible";

    public SinCupoException() {
        super(SIN_CUPO_MSJ);
    }
}
