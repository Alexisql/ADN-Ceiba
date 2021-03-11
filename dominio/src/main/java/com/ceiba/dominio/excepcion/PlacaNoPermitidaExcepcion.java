package com.ceiba.dominio.excepcion;

public class PlacaNoPermitidaExcepcion extends RuntimeException {

    public static final String PLACA_NO_PERMITIDA_MSJ = "No está autorizado a ingresar";

    public PlacaNoPermitidaExcepcion() {
        super(PLACA_NO_PERMITIDA_MSJ);
    }
}
