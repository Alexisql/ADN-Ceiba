package com.ceiba.infraestructura.anticorrupcion;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.infraestructura.bd.entidad.CarroEntidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CarroTraductor {

    public static Carro pasarCarroBDaCarroDominio(CarroEntidad carroEntidad) throws ParseException {
        Carro carro = new Carro(carroEntidad.placa);
        Calendar fechaIngreso = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechaIngreso.setTime(formatoFecha.parse(String.valueOf(fechaIngreso)));
        carro.modificarFechaIngreso(fechaIngreso);
        return carro;
    }

    public static CarroEntidad pasarCarroDominioACarroBD(Carro carro) {
        CarroEntidad carroEntidad = new CarroEntidad();
        carroEntidad.modificarPlaca(carro.obtenerPlaca());
        carroEntidad.modificarFechaIngreso(carro.obtenerFechaIngreso().toString());
        return carroEntidad;
    }

    public static List<Carro> pasarListaCarroDominioAListaCarroBD(List<CarroEntidad> listaCarros) throws ParseException {
        List<Carro> listaCarrosTemporal = new ArrayList<>();
        for (CarroEntidad carroEntidad : listaCarros) {
            listaCarrosTemporal.add(pasarCarroBDaCarroDominio(carroEntidad));
        }
        return listaCarrosTemporal;
    }
}
