package com.ceiba.adn.modelovista;

import android.content.Context;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ceiba.adn.R;
import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;
import com.ceiba.dominio.servicio.ServicioParqueadero;

import java.util.List;

import dagger.hilt.android.qualifiers.ApplicationContext;


public class ParqueaderoModeloVista extends ViewModel {

    public MutableLiveData<List<Vehiculo>> vehiculos;

    private ServicioParqueadero servicioParqueadero;

    private MutableLiveData<String> carroGuardado;

    private MutableLiveData<String> carroEliminado;

    private MutableLiveData<Integer> cantidadCarros;

    private MutableLiveData<String> motoGuardado;

    private MutableLiveData<String> motoEliminado;

    private MutableLiveData<Integer> cantidadMotos;

    private Context contexto;

    @ViewModelInject
    public ParqueaderoModeloVista(ServicioParqueadero servicioParqueadero, @ApplicationContext Context contexto) {
        this.contexto = contexto;
        this.servicioParqueadero = servicioParqueadero;
        obtenerVehiculos();
    }

    private void obtenerVehiculos() {
        if (vehiculos == null)
            this.vehiculos = new MutableLiveData<>();
        vehiculos = servicioParqueadero.obtenerVehiculos();
    }

    public MutableLiveData<List<Vehiculo>> obtenerListaVehiculosMutable() {
        return vehiculos;
    }

    public LiveData<String> guardarCarro(Carro carro) {
        if (carroGuardado == null)
            carroGuardado = new MutableLiveData<>();
        try {
            servicioParqueadero.guardarCarro(carro);
            carroGuardado.setValue(contexto.getString(R.string.guardado_exitoso));
        } catch (Exception excepcion) {
            carroGuardado.setValue(excepcion.getMessage());
        }
        return carroGuardado;
    }

    public LiveData<String> eliminarCarro(Carro carro) {
        if (carroEliminado == null)
            carroEliminado = new MutableLiveData<>();
        try {
            servicioParqueadero.eliminarCarro(carro);
            carroEliminado.setValue(contexto.getString(R.string.eliminado_exitoso));
        } catch (Exception excepcion) {
            carroEliminado.setValue(excepcion.getMessage());
        }
        return carroEliminado;
    }

    public LiveData<Integer> obtenerCantidadCarros() {
        if (cantidadCarros == null)
            cantidadCarros = new MutableLiveData<>();
        cantidadCarros.setValue(servicioParqueadero.obtenerCantidadCarros());
        return cantidadCarros;
    }

    public LiveData<String> guardarMoto(Moto moto) {
        if (motoGuardado == null)
            motoGuardado = new MutableLiveData<>();
        try {
            servicioParqueadero.guardarMoto(moto);
            motoGuardado.setValue(contexto.getString(R.string.guardado_exitoso));
        } catch (Exception excepcion) {
            motoGuardado.setValue(excepcion.getMessage());
        }
        return motoGuardado;
    }

    public LiveData<String> eliminarMoto(Moto moto) {
        if (motoEliminado == null)
            motoEliminado = new MutableLiveData<>();
        try {
            servicioParqueadero.eliminarMoto(moto);
            motoEliminado.setValue(contexto.getString(R.string.eliminado_exitoso));
        } catch (Exception excepcion) {
            motoEliminado.setValue(excepcion.getMessage());
        }
        return motoEliminado;
    }

    public LiveData<Integer> obtenerCantidadMotos() {
        if (cantidadMotos == null)
            cantidadMotos = new MutableLiveData<>();
        cantidadMotos.setValue(servicioParqueadero.obtenerCantidadMotos());
        return cantidadMotos;
    }

}
