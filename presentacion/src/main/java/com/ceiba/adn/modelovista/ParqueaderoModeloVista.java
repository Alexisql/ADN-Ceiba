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
import com.ceiba.dominio.servicio.ServicioParqueadero;

import java.util.List;

import dagger.hilt.android.qualifiers.ApplicationContext;


@SuppressLint("StaticFieldLeak")
public class ParqueaderoModeloVista extends ViewModel {

    private MutableLiveData<List<Vehiculo>> vehiculos;

    private final ServicioParqueadero servicioParqueadero;

    private MutableLiveData<Integer> totalPagar;

    private MutableLiveData<String> vehiculoGuardado;

    private final Context contexto;

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

    public LiveData<Integer> calcularValorTotalPagarVehiculo(Vehiculo vehiculo) {
        if (totalPagar == null)
            totalPagar = new MutableLiveData<>();
        if (vehiculo instanceof Carro) {
            Carro carro = (Carro) vehiculo;
            totalPagar.setValue(servicioParqueadero.calcularValorTotalPagarCarro(carro));
            servicioParqueadero.eliminarCarro(carro);
        } else {
            Moto moto = (Moto) vehiculo;
            totalPagar.setValue(servicioParqueadero.calcularValorTotalPagarMoto(moto));
            servicioParqueadero.eliminarMoto(moto);
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
                servicioParqueadero.guardarCarro(carro);
            } else {
                Moto moto = (Moto) vehiculo;
                servicioParqueadero.guardarMoto(moto);
            }
            vehiculos.getValue().add(vehiculo);
            vehiculoGuardado.setValue(contexto.getString(R.string.guardado_exitoso));
        } catch (Exception excepcion) {
            vehiculoGuardado.setValue(excepcion.getMessage());
        }
        return vehiculoGuardado;
    }

}
