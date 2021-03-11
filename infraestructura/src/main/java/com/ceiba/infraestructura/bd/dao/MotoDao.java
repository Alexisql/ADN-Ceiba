package com.ceiba.infraestructura.bd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ceiba.infraestructura.bd.entidad.MotoEntidad;

import java.util.List;

@Dao
public interface MotoDao {

    @Insert
    void guardarMoto(MotoEntidad motoEntidad);

    @Query("SELECT * FROM entidad_moto")
    List<MotoEntidad> obtenerListaMotos();

    @Delete
    void eliminarMoto(MotoEntidad motoEntidad);

    @Query("SELECT COUNT(*) FROM entidad_moto")
    byte obtenerCantidadMotos();
}
