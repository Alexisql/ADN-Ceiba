package com.ceiba.infraestructura.bd.entidad;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "entidad_carro")
public class CarroEntidad {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "placa")
    public String placa;

    @ColumnInfo(name = "fechaIngreso")
    public String fechaIngreso;

    public void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public void modificarFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
