package com.ceiba.infraestructura.repositorio;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.infraestructura.anticorrupcion.CarroTraductor;
import com.ceiba.infraestructura.bd.AdministradorBaseDatos;
import com.ceiba.infraestructura.bd.dao.CarroDao;
import com.ceiba.infraestructura.bd.entidad.CarroEntidad;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class CarroRepositorioRoom implements CarroRepositorio {

    private AdministradorBaseDatos administradorBaseDatos;
    private CarroDao carroDao;

    @Inject
    public CarroRepositorioRoom(@ApplicationContext Context contexto) {
        administradorBaseDatos = AdministradorBaseDatos.obtenerInstancia(contexto);
        carroDao = administradorBaseDatos.carDao();
    }

    @Override
    public List<Carro> obtenerCarros() {
        List<Carro> listaCarrosDominio = new ArrayList<>();
        ObtenerListaCarrosAsincrono obtenerListaCarrosAsincrono = new ObtenerListaCarrosAsincrono();
        try {
            List<CarroEntidad> carrosBD = obtenerListaCarrosAsincrono.execute().get();
            listaCarrosDominio.addAll(CarroTraductor.pasarListaCarroBDAListaCarroDominio(carrosBD));
        } catch (Exception excepcion) {
            Log.println(Log.ERROR, CarroRepositorioRoom.class.getName(), excepcion.getMessage());
        }
        return listaCarrosDominio;
    }

    @Override
    public void guardarCarro(Carro carro) {
        CarroEntidad carroEntidad = CarroTraductor.pasarCarroDominioACarroBD(carro);
        AdministradorBaseDatos.EJECUTOR_ESCRITURA_BD.execute(() -> {
            carroDao.guardarCarro(carroEntidad);
        });
    }

    @Override
    public void eliminarCarro(Carro carro) {
        EliminarCarroAsincrono eliminarCarroAsincrono = new EliminarCarroAsincrono();
        CarroEntidad carroEntidad = CarroTraductor.pasarCarroDominioACarroBD(carro);
        eliminarCarroAsincrono.execute(carroEntidad);
    }

    @Override
    public byte obtenerCantidadCarros() {
        byte cantidadCarros = 0;
        ObtenerCantidadCarrosAsincrono obtenerCantidadCarrosAsincrono = new ObtenerCantidadCarrosAsincrono();
        try {
            cantidadCarros = obtenerCantidadCarrosAsincrono.execute().get();
        } catch (Exception e) {
            Log.e("BD total carros", e.getMessage());
        }
        return cantidadCarros;
    }

    class ObtenerListaCarrosAsincrono extends AsyncTask<Void, Void, List<CarroEntidad>> {
        @Override
        protected List<CarroEntidad> doInBackground(Void... voids) {
            return carroDao.obtenerListaCarros();
        }
    }

    class EliminarCarroAsincrono extends AsyncTask<CarroEntidad, Void, Void> {
        @Override
        protected Void doInBackground(CarroEntidad... carroEntidad) {
            carroDao.eliminarCarro(carroEntidad[0]);
            return null;
        }
    }

    class ObtenerCantidadCarrosAsincrono extends AsyncTask<Void, Void, Byte> {
        @Override
        protected Byte doInBackground(Void... voids) {
            return carroDao.obtenerCantidadCarros();
        }
    }
}
