package com.ceiba.dominio;

import com.ceiba.dominio.entidad.Carro;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class CalculatorTest {

    Calendar fechaingreso;
    Calendar fechaSalida;
    Carro carro;

    @Before
    public void inicializarVariables() throws ParseException {
        carro = new Carro("AQW-5R4", "car");
        inicializarFechas();
    }

    public void inicializarFechas() throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        //Fecha de entrada
        fechaingreso = Calendar.getInstance();
        String fechaIngreso = "09-03-2021 6:20:56";
        Date fechaIngresoTemporal = formatoFecha.parse(fechaIngreso);
        fechaingreso.setTime(fechaIngresoTemporal);
        //Fecha de salida
        fechaSalida = Calendar.getInstance();
        String fechaSalida = "09-03-2021 10:30:56";
        Date fechaSalidaTemporal = formatoFecha.parse(fechaSalida);
        this.fechaSalida.setTime(fechaSalidaTemporal);
    }

    @Test
    public void calcularTotalHorasEnParqueadero_diferancia5Horas_exitoso() {
        //Act
        int subTotal = Calculator.calcularTotalHorasEnParqueadero(fechaingreso, fechaSalida);
        //Assert
        assertEquals(5, subTotal);
    }

    @Test
    public void valorSubTotalDeParqueadero_carroCon5HorasDeParqueo_exitoso() {
        //Act
        int subTotal = Calculator.valorSubTotalDeParqueadero(fechaingreso, fechaSalida, carro.getTipo());
        //Assert
        assertEquals(5000, subTotal);
    }
}