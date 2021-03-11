package com.ceiba.infraestructura.repositorio;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.repositorio.MotoRepositorio;
import com.ceiba.infraestructura.anticorrupcion.MotoTraductor;
import com.ceiba.infraestructura.bd.AdministradorBaseDatos;
import com.ceiba.infraestructura.bd.dao.MotoDao;
import com.ceiba.infraestructura.bd.entidad.MotoEntidad;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class MotoRepositorioRoom implements MotoRepositorio {

    private AdministradorBaseDatos administradorBaseDatos;
    private MotoDao motoDao;

    @Inject
    public MotoRepositorioRoom(@ApplicationContext Context contexto) {
        administradorBaseDatos = AdministradorBaseDatos.obtenerInstancia(contexto);
        motoDao = administradorBaseDatos.motoDao();
    }

    @Override
    public List<Moto> obtenerMotos() {
        List<Moto> listaMotosDominio = new ArrayList<>();
        ObtenerListaMotosAsincrono obtenerListaMotosAsincrono = new ObtenerListaMotosAsincrono();
        try {
            List<MotoEntidad> motosBD = obtenerListaMotosAsincrono.execute().get();
            MotoTraductor.pasarListaMotoDominioAListaMotoBD(motosBD);
        } catch (Exception e) {
            Log.e("BD listar motos", e.getMessage());
        }
        return listaMotosDominio;
    }

    @Override
    public void guardarMoto(Moto moto) {
        GuardarMotoAsincrono guardarMotoAsincrono = new GuardarMotoAsincrono();
        MotoEntidad motoEntidad = MotoTraductor.pasarMotoDominioAMotoBD(moto);
        guardarMotoAsincrono.execute(motoEntidad);
    }

    @Override
    public void eliminarMoto(Moto moto) {
        EliminarMotoAsincrono eliminarMotoAsincrono = new EliminarMotoAsincrono();
        MotoEntidad motoEntidad = MotoTraductor.pasarMotoDominioAMotoBD(moto);
        eliminarMotoAsincrono.execute(motoEntidad);
    }

    @Override
    public byte obtenerCantidadMotos() {
        byte cantidadMotos = 0;
        ObtenerCantidadMotosAsincrono obtenerCantidadMotosAsincrono = new ObtenerCantidadMotosAsincrono();
        try {
            cantidadMotos = obtenerCantidadMotosAsincrono.execute().get();
        } catch (Exception e) {
            Log.e("BD total motos", e.getMessage());
        }
        return cantidadMotos;
    }


    class ObtenerListaMotosAsincrono extends AsyncTask<Void, Void, List<MotoEntidad>> {
        @Override
        protected List<MotoEntidad> doInBackground(Void... voids) {
            return motoDao.obtenerListaMotos();
        }
    }

    class GuardarMotoAsincrono extends AsyncTask<MotoEntidad, Void, Void> {
        @Override
        protected Void doInBackground(MotoEntidad... motoEntidad) {
            motoDao.guardarMoto(motoEntidad[0]);
            return null;
        }
    }

    class EliminarMotoAsincrono extends AsyncTask<MotoEntidad, Void, Void> {
        @Override
        protected Void doInBackground(MotoEntidad... motoEntidad) {
            motoDao.eliminarMoto(motoEntidad[0]);
            return null;
        }
    }

    class ObtenerCantidadMotosAsincrono extends AsyncTask<Void, Void, Byte> {
        @Override
        protected Byte doInBackground(Void... voids) {
            return motoDao.obtenerCantidadMotos();
        }
    }
}
