package com.ceiba.dominio.entidad;

import java.util.Calendar;

public class Vehiculo {

    private String placa;
    private String tipo;
    private Calendar fechaIngreso;
    private final float HORA_EN_MILISEGUNDOS = 3600000;
    protected final int LIMITE_MAXIMO_HORAS = 9;
    protected final int CANTIDAD_HORAS_DIA = 24;

    public Vehiculo(String placa, String tipo) {
        modificarPlaca(placa);
        modificarFechaIngreso(Calendar.getInstance());
    }

    public String obtenerPlaca() {
        return placa;
    }

    private void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public Calendar obtenerFechaIngreso() {
        return fechaIngreso;
    }

    public void modificarFechaIngreso(Calendar fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int calcularTotalHorasEnParqueadero(Calendar fechaSalida) {
        long diferenciaEntreFechas = fechaSalida.getTimeInMillis() - obtenerFechaIngreso().getTimeInMillis();
        return (int) Math.ceil(diferenciaEntreFechas / HORA_EN_MILISEGUNDOS);
    }

}
