package com.ceiba.dominio.repositorio;

import com.ceiba.dominio.entidad.Moto;

import java.util.List;

public interface MotoRepositorio {

    List<Moto> obtenerMotos();

    void guardarMoto(Moto moto);

    void eliminarMoto(Moto moto);

    byte obtenerCantidadMotos();

}
