package com.ceiba.dominio.repositorio;

import com.ceiba.dominio.entidad.Carro;

import java.util.List;

public interface CarroRepositorio {

    List<Carro> obtenerCarros();

    void guardarCarro(Carro carro);

    void eliminarCarro(Carro carro);

    byte obtenerCantidadCarros();

}
