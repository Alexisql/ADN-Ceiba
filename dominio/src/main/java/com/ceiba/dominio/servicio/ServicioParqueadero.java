package com.ceiba.dominio.servicio;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;
import com.ceiba.dominio.excepcion.PlacaNoPermitidaExcepcion;
import com.ceiba.dominio.excepcion.SinCupoExcepcion;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.dominio.repositorio.MotoRepositorio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class ServicioParqueadero {

    private CarroRepositorio carroRepositorio;
    private MotoRepositorio motoRepositorio;
    private final String LETRA_INICIAL_PLACA = "A";
    private final int DIA_DE_PARQUEO = 3;

    @Inject
    public ServicioParqueadero(CarroRepositorio carroRepositorio, MotoRepositorio motoRepositorio) {
        this.carroRepositorio = carroRepositorio;
        this.motoRepositorio = motoRepositorio;
    }

    public List<Vehiculo> obtenerVehiculos() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        listaVehiculos.addAll(carroRepositorio.obtenerCarros());
        listaVehiculos.addAll(motoRepositorio.obtenerMotos());
        return listaVehiculos;
    }


    public void guardarCarro(Carro carro) {
        byte cantidadCarros = carroRepositorio.obtenerCantidadCarros();
        int diaActual = Calendar.getInstance().getFirstDayOfWeek();
        if (cantidadCarros == carro.CANTIDAD_MAXIMA_EN_PARQUEADERO) {
            throw new SinCupoExcepcion();
        } else if (validarPlaca(carro.obtenerPlaca(), diaActual)) {
            throw new PlacaNoPermitidaExcepcion();
        } else {
            carroRepositorio.guardarCarro(carro);
        }
    }

    public void guardarMoto(Moto moto) {
        byte cantidadMotos = motoRepositorio.obtenerCantidadMotos();
        int diaActual = Calendar.getInstance().getFirstDayOfWeek();
        if (cantidadMotos == moto.CANTIDAD_MAXIMA_EN_PARQUEADERO) {
            throw new SinCupoExcepcion();
        } else if (validarPlaca(moto.obtenerPlaca(), diaActual)) {
            throw new PlacaNoPermitidaExcepcion();
        } else {
            motoRepositorio.guardarMoto(moto);
        }
    }

    public void eliminarCarro(Carro carro) {
        carroRepositorio.eliminarCarro(carro);
    }

    public void eliminarMoto(Moto moto) {
        motoRepositorio.eliminarMoto(moto);
    }

    public boolean validarPlaca(String placa, int diaActual) {
        return (placa.startsWith(LETRA_INICIAL_PLACA) && (diaActual < DIA_DE_PARQUEO));
    }

    public int calcularValorTotalPagarMoto(Moto moto) {
        return moto.calcularValorTotalDeParqueadero(Calendar.getInstance());
    }

    public int calcularValorTotalPagarCarro(Carro carro) {
        return carro.calcularValorTotalDeParqueadero(Calendar.getInstance());
    }
}
