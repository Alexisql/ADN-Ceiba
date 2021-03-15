package com.ceiba.adn.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceiba.adn.MainActivity;
import com.ceiba.adn.R;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.entidad.Vehiculo;

import java.util.List;

public class VehiculoAdaptador extends RecyclerView.Adapter<VehiculoAdaptador.VehiculoViewHolder> {

    private List<Vehiculo> vehiculos;
    private Activity actividad;

    public VehiculoAdaptador(List<Vehiculo> vehiculos, Activity actividad) {
        this.vehiculos = vehiculos;
        this.actividad = actividad;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehiculoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_vehiculos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = vehiculos.get(position);
        holder.iniciarElementos(vehiculo);
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {

        private TextView placa;
        private TextView fechaIngreso;
        private TextView cilindraje;
        private LinearLayout contenedorCilindraje;
        private Button btnCobrar;

        public VehiculoViewHolder(@NonNull View vista) {
            super(vista);
            placa = vista.findViewById(R.id.placa);
            fechaIngreso = vista.findViewById(R.id.fechaIngreso);
            contenedorCilindraje = vista.findViewById(R.id.contenedorCilindraje);
            cilindraje = vista.findViewById(R.id.cilingraje);
            btnCobrar = vista.findViewById(R.id.btn_cobrar);
        }

        public void iniciarElementos(Vehiculo vehiculo) {
            this.placa.setText(vehiculo.obtenerPlaca());
            this.fechaIngreso.setText(vehiculo.obtenerFechaIngreso().getTime().toString());
            if (vehiculo instanceof Moto) {
                Moto moto = (Moto) vehiculo;
                contenedorCilindraje.setVisibility(View.VISIBLE);
                this.cilindraje.setText(String.valueOf(moto.obtenerCilindraje()));
            }
            btnCobrar.setOnClickListener(v -> cobrarParqueadero(vehiculo));
        }

        private void cobrarParqueadero(Vehiculo vehiculo) {
            if (actividad instanceof MainActivity) {
                MainActivity actividadPrincipal = (MainActivity) actividad;
                actividadPrincipal.cobrarParqueadero(vehiculo);
            }
        }
    }
}
