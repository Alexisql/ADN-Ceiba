package com.ceiba.infraestructura.repositorio;

import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.repositorio.MotoRepositorio;

import java.util.List;

public class MotoRepositorioRoom implements MotoRepositorio {



    @Override
    public List<Moto> obtenerMotos() {
        return null;
    }

    @Override
    public void guardarMoto(Moto moto) {

    }

    @Override
    public void eliminarMoto(Moto moto) {

    }

    @Override
    public byte obtenerCantidadMotos() {
        return 0;
    }
}
