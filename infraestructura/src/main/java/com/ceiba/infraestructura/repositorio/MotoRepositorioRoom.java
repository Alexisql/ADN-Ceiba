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
            listaMotosDominio.addAll(MotoTraductor.pasarListaMotoBDAListaMotoDominio(motosBD));
        } catch (Exception excepcion) {
            Log.println(Log.ERROR, MotoRepositorioRoom.class.getName(), excepcion.getMessage());
        }
        return listaMotosDominio;
    }

    @Override
    public void guardarMoto(Moto moto) {
        MotoEntidad motoEntidad = MotoTraductor.pasarMotoDominioAMotoBD(moto);
        AdministradorBaseDatos.EJECUTOR_ESCRITURA_BD.execute(() -> {
            motoDao.guardarMoto(motoEntidad);
        });
    }

    @Override
    public void eliminarMoto(Moto moto) {
        MotoEntidad motoEntidad = MotoTraductor.pasarMotoDominioAMotoBD(moto);
        AdministradorBaseDatos.EJECUTOR_ESCRITURA_BD.execute(() -> {
            motoDao.eliminarMoto(motoEntidad.placa);
        });
    }

    @Override
    public byte obtenerCantidadMotos() {
        byte cantidadMotos = 0;
        ObtenerCantidadMotosAsincrono obtenerCantidadMotosAsincrono = new ObtenerCantidadMotosAsincrono();
        try {
            cantidadMotos = obtenerCantidadMotosAsincrono.execute().get();
        } catch (Exception excepcion) {
            Log.println(Log.ERROR, MotoRepositorioRoom.class.getName(), excepcion.getMessage());
        }
        return cantidadMotos;
    }


    class ObtenerListaMotosAsincrono extends AsyncTask<Void, Void, List<MotoEntidad>> {
        @Override
        protected List<MotoEntidad> doInBackground(Void... voids) {
            return motoDao.obtenerListaMotos();
        }
    }

    class ObtenerCantidadMotosAsincrono extends AsyncTask<Void, Void, Byte> {
        @Override
        protected Byte doInBackground(Void... voids) {
            return motoDao.obtenerCantidadMotos();
        }
    }
}
