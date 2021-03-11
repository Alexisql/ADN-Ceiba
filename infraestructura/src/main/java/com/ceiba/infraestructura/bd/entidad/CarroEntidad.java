package com.ceiba.infraestructura.bd.entidad;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "entidad_carro")
public class CarroEntidad {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "placa")
    public String placa;

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "fechaIngreso")
    public String fechaIngreso;

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
