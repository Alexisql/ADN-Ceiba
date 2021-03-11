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
        ObtenerCarrosAsyncTask obtenerCarrosAsyncTask = new ObtenerCarrosAsyncTask();
        try {
            List<CarroEntidad> carrosBD = obtenerCarrosAsyncTask.execute().get();
            CarroTraductor.pasarListaCarroDominioAListaCarroBD(carrosBD);
        } catch (Exception e) {
            Log.e("Consulta BD carro", e.getMessage());
        }
        return listaCarrosDominio;
    }

    @Override
    public void guardarCarro(Carro carro) {
        GuardarCarroAsyncTask guardarCarroAsyncTask = new GuardarCarroAsyncTask();
        CarroEntidad carroEntidad = CarroTraductor.pasarCarroDominioACarroBD(carro);
        guardarCarroAsyncTask.execute(carroEntidad);
    }

    @Override
    public void eliminarCarro(Carro carro) {

    }

    @Override
    public byte obtenerCantidadCarros() {
        return 0;
    }

    class ObtenerCarrosAsyncTask extends AsyncTask<Void, Void, List<CarroEntidad>> {
        @Override
        protected List<CarroEntidad> doInBackground(Void... voids) {
            return carroDao.obtenerListaCarros();
        }
    }

    class GuardarCarroAsyncTask extends AsyncTask<CarroEntidad, Void, Void> {
        @Override
        protected Void doInBackground(CarroEntidad... carEntities) {
            carroDao.guardarCarro(carEntities[0]);
            return null;
        }
    }
}
