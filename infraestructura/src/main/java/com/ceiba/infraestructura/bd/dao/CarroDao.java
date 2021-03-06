package com.ceiba.infraestructura.bd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ceiba.infraestructura.bd.entidad.CarroEntidad;

import java.util.List;

@Dao
public interface CarroDao {

    @Insert
    void guardarCarro(CarroEntidad carroEntidad);

    @Query("SELECT * FROM entidad_carro")
    List<CarroEntidad> obtenerListaCarros();

    @Query("DELETE FROM entidad_carro WHERE placa = :placaCarro")
    void eliminarCarro(String placaCarro);

    @Query("SELECT COUNT(*) FROM entidad_carro")
    byte obtenerCantidadCarros();

}
