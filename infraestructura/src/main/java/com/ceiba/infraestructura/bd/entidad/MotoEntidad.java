package com.ceiba.infraestructura.bd.entidad;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "entidad_moto")
public class MotoEntidad {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "placa")
    public String placa;

    @ColumnInfo(name = "fechaIngreso")
    public String fechaIngreso;

    @ColumnInfo(name = "cilindraje")
    public int cilindraje;

    public void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public void modificarFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void modificarCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }
}
