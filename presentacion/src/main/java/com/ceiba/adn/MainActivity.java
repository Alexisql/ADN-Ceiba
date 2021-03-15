package com.ceiba.adn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.ceiba.adn.adaptador.VehiculoAdaptador;
import com.ceiba.adn.modelovista.ParqueaderoModeloVista;
import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        btnIngresarVehiculo.setOnClickListener(v -> {
            if (true) {
                Carro carro = new Carro("SDE-KJ8", "carro");
                parqueaderoModeloVista.guardarCarro(carro).observe(this, vehiculo -> {
                    vehiculoAdaptador.notifyDataSetChanged();
                });
            }
        });
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
        AtomicInteger valorTotalPagar = new AtomicInteger();
        if (vehiculo instanceof Carro) {
            Carro carro = (Carro) vehiculo;
            parqueaderoModeloVista.calcularValorTotalPagarCarro(carro).observe(this, valorPagar -> {
                valorTotalPagar.set(valorPagar);
            });
        } else {
            Moto moto = (Moto) vehiculo;
            parqueaderoModeloVista.calcularValorTotalPagarMoto(moto).observe(this, valorPagar -> {
                valorTotalPagar.set(valorPagar);
            });
        }
        Toast.makeText(this, "Total a Pagar: " + valorTotalPagar.get(), Toast.LENGTH_SHORT).show();
        vehiculoAdaptador.notifyDataSetChanged();
    }
}