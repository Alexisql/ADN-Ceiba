package com.ceiba.adn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ceiba.adn.adaptador.VehiculoAdaptador;
import com.ceiba.adn.modelovista.ParqueaderoModeloVista;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ParqueaderoModeloVista parqueaderoModeloVista;
    private RecyclerView vistaReciclada;
    private VehiculoAdaptador vehiculoAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parqueaderoModeloVista = new ViewModelProvider(this).get(ParqueaderoModeloVista.class);

        vistaReciclada = findViewById(R.id.vistaRecicladaVehiculos);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        vistaReciclada.setLayoutManager(lm);
        parqueaderoModeloVista.obtenerListaVehiculosMutable().observe(this, vehiculos -> {
            vehiculoAdaptador = new VehiculoAdaptador(vehiculos);
            vistaReciclada.setAdapter(vehiculoAdaptador);
        });

    }
}