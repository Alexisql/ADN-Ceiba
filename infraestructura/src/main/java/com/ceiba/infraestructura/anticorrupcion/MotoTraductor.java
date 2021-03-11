package com.ceiba.infraestructura.anticorrupcion;

import com.ceiba.dominio.entidad.Moto;
import com.ceiba.infraestructura.bd.entidad.MotoEntidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MotoTraductor {

    public static Moto pasarMotoBDaMotoDominio(MotoEntidad motoEntidad) throws ParseException {
        Moto moto = new Moto(motoEntidad.placa, motoEntidad.cilindraje);
        Calendar fechaIngreso = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaIngreso.setTime(formatoFecha.parse(String.valueOf(motoEntidad.fechaIngreso)));
        moto.modificarFechaIngreso(fechaIngreso);
        return moto;
    }

    public static MotoEntidad pasarMotoDominioAMotoBD(Moto moto) {
        MotoEntidad motoEntidad = new MotoEntidad();
        motoEntidad.modificarPlaca(moto.obtenerPlaca());
        motoEntidad.modificarCilindraje(moto.obtenerCilindraje());
        motoEntidad.modificarFechaIngreso(moto.obtenerFechaIngreso().toString());
        return motoEntidad;
    }

    public static List<Moto> pasarListaMotoDominioAListaMotoBD(List<MotoEntidad> listaMotos) throws ParseException {
        List<Moto> listaMotosTemporal = new ArrayList<>();
        for (MotoEntidad motoEntidad : listaMotos) {
            listaMotosTemporal.add(pasarMotoBDaMotoDominio(motoEntidad));
        }
        return listaMotosTemporal;
    }
}
