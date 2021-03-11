package com.ceiba.dominio.entidad;

import java.util.Calendar;

public class Vehiculo {

    private String placa;
    private Calendar fechaIngreso;
    private final float HORA_EN_MILISEGUNDOS = 3600000;
    protected final int LIMITE_MAXIMO_HORAS = 9;
    protected final int CANTIDAD_HORAS_DIA = 24;

    public Vehiculo(String placa) {
        setPlaca(placa);
        setFechaIngreso(Calendar.getInstance());
    }

    public String getPlaca() {
        return placa;
    }

    private void setPlaca(String placa) {
        this.placa = placa;
    }

    public Calendar getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Calendar fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int calcularTotalHorasEnParqueadero(Calendar fechaSalida) {
        long diferenciaEntreFechas = fechaSalida.getTimeInMillis() - getFechaIngreso().getTimeInMillis();
        return (int) Math.ceil(diferenciaEntreFechas / HORA_EN_MILISEGUNDOS);
    }

}
