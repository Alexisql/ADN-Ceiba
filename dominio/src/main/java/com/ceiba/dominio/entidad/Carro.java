package com.ceiba.dominio.entidad;

public class Carro extends Vehiculo {

    public static final short VALOR_HORA_PARQUEADERO = 1000;
    public static final short VALOR_DIA_PARQUEADERO = 8000;
    public static final short CANTIDAD_MAXIMA_EN_PARQUEADERO = 20;

    public Carro(String placa) {
        super(placa);
    }
}
