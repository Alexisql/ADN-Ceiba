package com.ceiba.dominio.servicio;

import com.ceiba.dominio.Calculator;
import com.ceiba.dominio.Constants;
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
        if (cantidadCarros == 20) {
            throw new SinCupoExcepcion();
        } else if (validarPlaca(carro.getPlaca(), diaActual)) {
            throw new PlacaNoPermitidaExcepcion();
        } else {
            carroRepositorio.guardarCarro(carro);
        }
    }

    public void guardarMoto(Moto moto) {
        byte cantidadMotos = motoRepositorio.obtenerCantidadMotos();
        int diaActual = Calendar.getInstance().getFirstDayOfWeek();
        if (cantidadMotos == 10) {
            throw new SinCupoExcepcion();
        } else if (validarPlaca(moto.getPlaca(), diaActual)) {
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
        return (placa.startsWith("A") && (diaActual < 3));
    }

    public int valorTotalParqueaderoMoto(Moto moto) {
        Calendar fechaIngreso = moto.getFechaIngreso();
        Calendar fechaSalida = Calendar.getInstance();
        int valorTotal = Calculator.valorSubTotalDeParqueadero(fechaIngreso, fechaSalida, moto.getTipo());
        if (moto.getCilindraje() > Constants.CYLINDER_MOTORCYCLE) {
            valorTotal += Constants.EXEDENT_MOTORCYCLE;
        }
        return valorTotal;
    }

    public int valorTotalParqueaderoCarro(Carro carro) {
        Calendar fechaIngreso = carro.getFechaIngreso();
        Calendar fechaSalida = Calendar.getInstance();
        return Calculator.valorSubTotalDeParqueadero(fechaIngreso, fechaSalida, carro.getTipo());
    }

}
