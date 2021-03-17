package com.ceiba.dominio.servicio;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;
import com.ceiba.dominio.excepcion.PlacaNoPermitidaExcepcion;
import com.ceiba.dominio.excepcion.SinCupoExcepcion;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.dominio.repositorio.MotoRepositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class ServicioParqueadero {

    private CarroRepositorio carroRepositorio;
    private MotoRepositorio motoRepositorio;
    private static final String LETRA_INICIAL_PLACA = "A";
    private static final int DIA_LUNES = 1;
    private static final int DIA_DOMINGO = 7;

    @Inject
    public ServicioParqueadero(CarroRepositorio carroRepositorio, MotoRepositorio motoRepositorio) {
        this.carroRepositorio = carroRepositorio;
        this.motoRepositorio = motoRepositorio;
    }

    public MutableLiveData<List<Vehiculo>> obtenerVehiculos() {
        MutableLiveData<List<Vehiculo>> listaMutable = new MutableLiveData<>();
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        listaVehiculos.addAll(carroRepositorio.obtenerCarros());
        listaVehiculos.addAll(motoRepositorio.obtenerMotos());
        listaMutable.setValue(listaVehiculos);
        return listaMutable;
    }

    public void guardarCarro(Carro carro) {
        byte cantidadCarros = carroRepositorio.obtenerCantidadCarros();
        int diaActual = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            diaActual = LocalDate.now().getDayOfWeek().getValue();
        }
        if (cantidadCarros == carro.CANTIDAD_MAXIMA_EN_PARQUEADERO) {
            throw new SinCupoExcepcion();
        } else if (validarPlaca(carro.obtenerPlaca(), diaActual)) {
            throw new PlacaNoPermitidaExcepcion();
        }
        carroRepositorio.guardarCarro(carro);
    }

    public void guardarMoto(Moto moto) {
        byte cantidadMotos = motoRepositorio.obtenerCantidadMotos();
        int diaActual = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            diaActual = LocalDate.now().getDayOfWeek().getValue();
        }
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
        return (placa.startsWith(LETRA_INICIAL_PLACA) && (diaActual == DIA_LUNES || diaActual == DIA_DOMINGO));
    }

    public int calcularValorTotalPagarMoto(Moto moto) {
        return moto.calcularValorTotalDeParqueadero(Calendar.getInstance());
    }

    public int calcularValorTotalPagarCarro(Carro carro) {
        return carro.calcularValorTotalDeParqueadero(Calendar.getInstance());
    }
    
}
