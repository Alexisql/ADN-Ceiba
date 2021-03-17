package com.ceiba.adn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.ceiba.adn.adaptador.VehiculoAdaptador;
import com.ceiba.adn.modelovista.ParqueaderoModeloVista;
import com.ceiba.dominio.entidad.Vehiculo;


import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ParqueaderoModeloVista parqueaderoModeloVista;
    private RecyclerView vistaReciclada;
    private VehiculoAdaptador vehiculoAdaptador;
    private Button btnIngresarVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarElementos();
        btnIngresarVehiculo.setOnClickListener(v -> DialogoVehiculo.crearDialogoGuardarVehiculo(this).show());
        parqueaderoModeloVista.obtenerListaVehiculosMutable().observe(this, this::actualizarAdaptador);
    }

    private void iniciarElementos() {
        vistaReciclada = findViewById(R.id.vistaRecicladaVehiculos);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        vistaReciclada.setLayoutManager(lm);
        btnIngresarVehiculo = findViewById(R.id.btnIngresarVehiculo);
        parqueaderoModeloVista = new ViewModelProvider(this).get(ParqueaderoModeloVista.class);
    }

    private void actualizarAdaptador(List<Vehiculo> vehiculos) {
        vehiculoAdaptador = new VehiculoAdaptador(vehiculos, this);
        vistaReciclada.setAdapter(vehiculoAdaptador);
    }

    public void cobrarParqueadero(Vehiculo vehiculo) {
        parqueaderoModeloVista.calcularValorTotalPagarVehiculo(vehiculo).observe(this, totalPagar -> {
            Toast.makeText(this, "Total a Pagar: " + totalPagar, Toast.LENGTH_SHORT).show();
            vistaReciclada.getAdapter().notifyDataSetChanged();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarVehiculo(Vehiculo vehiculo, AlertDialog dialogo) {
        parqueaderoModeloVista.guardarVehiculo(vehiculo).observe(this, vehiculoGuardado -> {
            Toast.makeText(this, vehiculoGuardado, Toast.LENGTH_SHORT).show();
            dialogo.dismiss();
            vehiculoAdaptador.notifyDataSetChanged();
        });
    }
}