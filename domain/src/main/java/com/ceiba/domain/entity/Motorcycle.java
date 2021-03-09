package com.ceiba.domain.entity;

public class Motorcycle extends Vehicle {

    private short cilindraje;

    public Motorcycle(String placa, String tipo, short cilindraje) {
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
