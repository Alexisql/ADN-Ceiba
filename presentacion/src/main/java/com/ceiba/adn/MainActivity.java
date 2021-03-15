package com.ceiba.adn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ceiba.adn.adaptador.VehiculoAdaptador;
import com.ceiba.adn.modelovista.ParqueaderoModeloVista;
import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
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
        btnIngresarVehiculo.setOnClickListener(v -> crearDialogoGuardarVehiculo());
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


    private void crearDialogoGuardarVehiculo() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        LayoutInflater disenio = this.getLayoutInflater();
        View vista = disenio.inflate(R.layout.dialogo_registrar_vehiculo, null);
        dialogo.setView(vista);
        LinearLayout contenedorCilindraje = vista.findViewById(R.id.contenedorCilindraje);
        RadioButton tipoCarro = vista.findViewById(R.id.tipoCarro);
        RadioButton tipoMoto = vista.findViewById(R.id.tipoMoto);
        EditText placa = vista.findViewById(R.id.placa);
        EditText cilindraje = vista.findViewById(R.id.cilindraje);
        Button btnAgregar = vista.findViewById(R.id.btn_agregar);
        Button btnCancelar = vista.findViewById(R.id.btn_cancelar);
        AlertDialog dialogoTmp = dialogo.create();
        dialogoTmp.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogoTmp.show();
        tipoMoto.setOnClickListener(v -> contenedorCilindraje.setVisibility(View.VISIBLE));
        tipoCarro.setOnClickListener(v -> {
            if (contenedorCilindraje.getVisibility() == View.VISIBLE)
                contenedorCilindraje.setVisibility(View.GONE);
        });
        btnCancelar.setOnClickListener(v -> dialogoTmp.dismiss());
        btnAgregar.setOnClickListener(v -> {
            Vehiculo vehiculo = crearVehiculo(tipoMoto, placa.getText().toString(), Integer.parseInt(cilindraje.getText().toString()));
            guardarVehiculo(vehiculo, dialogoTmp);
        });
    }

    private Vehiculo crearVehiculo(RadioButton rdMoto, String placa, int cilindraje) {
        Vehiculo vehiculo;
        if (rdMoto.isChecked()) {
            vehiculo = new Moto(placa, cilindraje);
        } else {
            vehiculo = new Carro(placa);
        }
        return vehiculo;
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

    private void guardarVehiculo(Vehiculo vehiculo, AlertDialog dialogo) {
        parqueaderoModeloVista.guardarVehiculo(vehiculo).observe(this, vehiculoGuardado -> {
            Toast.makeText(this, vehiculoGuardado, Toast.LENGTH_SHORT).show();
            dialogo.dismiss();
            vehiculoAdaptador.notifyDataSetChanged();
        });
    }
}