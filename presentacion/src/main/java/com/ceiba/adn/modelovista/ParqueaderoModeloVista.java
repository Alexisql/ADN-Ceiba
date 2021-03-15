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

    private MutableLiveData<String> carroGuardado;

    private MutableLiveData<Integer> valorPagarCarro;

    private MutableLiveData<String> motoGuardado;

    private MutableLiveData<Integer> valorPagarMoto;

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

    public LiveData<Integer> calcularValorTotalPagarCarro(Carro carro) {
        if (valorPagarCarro == null)
            valorPagarCarro = new MutableLiveData<>();
        valorPagarCarro.setValue(servicioParqueadero.calcularValorTotalPagarCarro(carro));
        servicioParqueadero.eliminarCarro(carro);
        return valorPagarCarro;
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

    public LiveData<Integer> calcularValorTotalPagarMoto(Moto moto) {
        if (valorPagarMoto == null)
            valorPagarMoto = new MutableLiveData<>();
        valorPagarMoto.setValue(servicioParqueadero.calcularValorTotalPagarMoto(moto));
        servicioParqueadero.eliminarMoto(moto);
        return valorPagarMoto;
    }

}
