package com.ceiba.dominio.entidad;

public class Moto extends Vehiculo {

    private short cilindraje;

    public Moto(String placa, String tipo, short cilindraje) {
        super(placa, tipo);
        setCilindraje(cilindraje);
    }

    public short getCilindraje() {
        return cilindraje;
    }

    private void setCilindraje(short cilindraje) {
        this.cilindraje = cilindraje;
    }

}
