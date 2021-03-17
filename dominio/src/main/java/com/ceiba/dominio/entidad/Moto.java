package com.ceiba.dominio.entidad;

public class Moto extends Vehiculo {

    private int cilindraje;
    public static final short VALOR_HORA_PARQUEADERO = 500;
    public static final short VALOR_DIA_PARQUEADERO = 4000;
    public static final short CANTIDAD_MAXIMA_EN_PARQUEADERO = 10;

    public Moto(String placa, int cilindraje) {
        super(placa);
        modificarCilindraje(cilindraje);
    }

    public int obtenerCilindraje() {
        return cilindraje;
    }

    private void modificarCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

}
