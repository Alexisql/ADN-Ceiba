package com.ceiba.adn.modelovista;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ceiba.adn.R;
import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;
import com.ceiba.dominio.servicio.ParqueaderoServicio;

import java.util.List;

import dagger.hilt.android.qualifiers.ApplicationContext;


@SuppressLint("StaticFieldLeak")
public class ParqueaderoModeloVista extends ViewModel {

    private MutableLiveData<List<Vehiculo>> vehiculos;

    private final ParqueaderoServicio parqueaderoServicio;

    private MutableLiveData<Integer> totalPagar;

    private MutableLiveData<String> vehiculoGuardado;

    private final Context contexto;

    @ViewModelInject
    public ParqueaderoModeloVista(ParqueaderoServicio parqueaderoServicio, @ApplicationContext Context contexto) {
        this.contexto = contexto;
        this.parqueaderoServicio = parqueaderoServicio;
        obtenerVehiculos();
    }

    private void obtenerVehiculos() {
        if (vehiculos == null)
            this.vehiculos = new MutableLiveData<>();
        vehiculos = parqueaderoServicio.obtenerVehiculos();
    }

    public MutableLiveData<List<Vehiculo>> obtenerListaVehiculosMutable() {
        return vehiculos;
    }

    public LiveData<Integer> calcularValorTotalPagarVehiculo(Vehiculo vehiculo) {
        if (totalPagar == null)
            totalPagar = new MutableLiveData<>();
        if (vehiculo instanceof Carro) {
            Carro carro = (Carro) vehiculo;
            totalPagar.setValue(parqueaderoServicio.calcularValorTotalPagarCarro(carro));
            parqueaderoServicio.eliminarCarro(carro);
        } else {
            Moto moto = (Moto) vehiculo;
            totalPagar.setValue(parqueaderoServicio.calcularValorTotalPagarMoto(moto));
            parqueaderoServicio.eliminarMoto(moto);
        }
        vehiculos.getValue().remove(vehiculo);
        return totalPagar;
    }

    public LiveData<String> guardarVehiculo(Vehiculo vehiculo) {
        if (vehiculoGuardado == null)
            vehiculoGuardado = new MutableLiveData<>();
        try {
            if (vehiculo instanceof Carro) {
                Carro carro = (Carro) vehiculo;
                parqueaderoServicio.guardarCarro(carro);
            } else {
                Moto moto = (Moto) vehiculo;
                parqueaderoServicio.guardarMoto(moto);
            }
            vehiculos.getValue().add(vehiculo);
            vehiculoGuardado.setValue(contexto.getString(R.string.guardado_exitoso));
        } catch (Exception excepcion) {
            vehiculoGuardado.setValue(excepcion.getMessage());
        }
        return vehiculoGuardado;
    }

}
