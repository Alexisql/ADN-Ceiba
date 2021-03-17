package com.ceiba.dominio.entidad;

import java.util.Calendar;

public class Vehiculo {

    private String placa;
    private Calendar fechaIngreso;
    private static final float HORA_EN_MILISEGUNDOS = 3600000;
    protected static final int LIMITE_MAXIMO_HORAS = 9;
    protected static final int CANTIDAD_HORAS_DIA = 24;

    public Vehiculo(String placa) {
        modificarPlaca(placa);
        modificarFechaIngreso(Calendar.getInstance());
    }

    public String obtenerPlaca() {
        return placa;
    }

    private void modificarPlaca(String placa) {
        this.placa = placa;
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
