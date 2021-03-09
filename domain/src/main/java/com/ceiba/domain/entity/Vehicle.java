package com.ceiba.domain.entity;

import java.util.Calendar;

public class Vehicle {

    private String placa;
    private String tipo;
    private Calendar fechaIngreso;

    public Vehicle(String placa, String tipo) {
        setPlaca(placa);
        setTipo(tipo);
        setFechaIngreso(Calendar.getInstance());
    }

    public String getPlaca() {
        return placa;
    }

    private void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Calendar getFechaIngreso() {
        return fechaIngreso;
    }

    private void setFechaIngreso(Calendar fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
