package com.ceiba.dominio.excepcion;

public class SinCupoExcepcion extends RuntimeException {

    public static final String SIN_CUPO_MSJ = "No hay cupo disponible";

    public SinCupoExcepcion() {
        super(SIN_CUPO_MSJ);
    }
}
