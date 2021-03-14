package com.ceiba.adn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.ceiba.adn.modelovista.ParqueaderoModeloVista;
import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Vehiculo;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ParqueaderoModeloVista parqueaderoModeloVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parqueaderoModeloVista = new ViewModelProvider(this).get(ParqueaderoModeloVista.class);

        for (Vehiculo vehiculo : parqueaderoModeloVista.vehiculos.getValue()) {
            System.out.println("***************** " + vehiculo.obtenerPlaca() + " *****************");
        }

        Carro carro = new Carro("ALEXIS");
        parqueaderoModeloVista.guardarCarro(carro).observe(this, vehiculoGuardado -> {
            Toast.makeText(getApplicationContext(), vehiculoGuardado + carro.obtenerPlaca(), Toast.LENGTH_SHORT).show();
        });
    }
}