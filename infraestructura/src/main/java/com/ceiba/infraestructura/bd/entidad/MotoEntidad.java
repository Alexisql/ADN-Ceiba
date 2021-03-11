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

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "fechaIngreso")
    public String fechaIngreso;

    @ColumnInfo(name = "cilindraje")
    public int cilindraje;
}
