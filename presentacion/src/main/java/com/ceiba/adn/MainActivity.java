package com.ceiba.adn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.servicio.ServicioParqueadero;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ServicioParqueadero servicioParqueadero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Carro carro = new Carro("AXS-VF3");
        servicioParqueadero.guardarCarro(carro);

    }
}