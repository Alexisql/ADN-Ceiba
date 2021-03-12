package com.ceiba.adn.moduloid;

import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.dominio.repositorio.MotoRepositorio;
import com.ceiba.infraestructura.repositorio.CarroRepositorioRoom;
import com.ceiba.infraestructura.repositorio.MotoRepositorioRoom;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn({ActivityComponent.class})
public abstract class ModuloVehiculo {

    @Binds
    public abstract CarroRepositorio bindCarroRepositorio(CarroRepositorioRoom carroRepositorioRoom);

    @Binds
    public abstract MotoRepositorio binMotoRepositorio(MotoRepositorioRoom motoRepositorioRoom);
}
