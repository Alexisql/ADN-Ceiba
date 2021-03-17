package com.ceiba.dominio;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.excepcion.PlacaNoPermitidaExcepcion;
import com.ceiba.dominio.excepcion.SinCupoExcepcion;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.dominio.repositorio.MotoRepositorio;
import com.ceiba.dominio.servicio.ServicioParqueadero;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServicioParqueaderoUnitTest {

    @Mock
    private CarroRepositorio carroRepositorio;

    @Mock
    private MotoRepositorio motoRepositorio;

    private ServicioParqueadero servicioParqueadero;

    @Before
    public void inicializarVariables() {
        carroRepositorio = Mockito.mock(CarroRepositorio.class);
        motoRepositorio = Mockito.mock(MotoRepositorio.class);
        servicioParqueadero = new ServicioParqueadero(carroRepositorio, motoRepositorio);
    }

    private String iniciarExcepcionSinCupo() {
        return "No hay cupo disponible";
    }

    private Calendar fechaEntrada() throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Calendar fechaIngreso = Calendar.getInstance();
        Date fechaIngresoTemporal = formatoFecha.parse("08-03-2021 6:20:56");
        fechaIngreso.setTime(fechaIngresoTemporal);
        return fechaIngreso;
    }

    @Test
    public void validarPlaca_placaIniciadaEnAyElDiaViernes_exitoso() {
        //Arrange
        String placa = "ASD-J87";
        int diaViernes = 5;
        boolean resultadoEsperado;
        //Act
        resultadoEsperado = servicioParqueadero.validarPlaca(placa, diaViernes);
        //Asset
        assertEquals(false, resultadoEsperado);
    }

    @Test
    public void validarPlaca_placaIniciadaEnAyElDiaLunes_exitoso() {
        //Arrange
        String placa = "ASD-J87";
        int diaLunes = 1;
        boolean resultadoEsperado;
        //Act
        resultadoEsperado = servicioParqueadero.validarPlaca(placa, diaLunes);
        //Asset
        assertEquals(true, resultadoEsperado);

    }

    @Test
    public void guardarCarro_sinCupoParaIngresar_exitoso() {
        //Arrange
        Carro carro = new Carro("AQW-5R4");
        when(carroRepositorio.obtenerCantidadCarros()).thenReturn((byte) carro.CANTIDAD_MAXIMA_EN_PARQUEADERO);
        //Act
        try {
            servicioParqueadero.guardarCarro(carro);
        } catch (SinCupoExcepcion excepcion) {
            //Assert
            assertEquals(iniciarExcepcionSinCupo(), excepcion.getMessage());
        }
    }

    @Test
    public void guardarCarro_placaNoAutorizaDiaLunes_exitoso() throws ParseException {
        //Arrange
        Carro carro = new Carro("AQW-5R4");
        carro.modificarFechaIngreso(fechaEntrada());
        String excepcionPlacaNoPermitida = "No está autorizado a ingresar";
        when(carroRepositorio.obtenerCantidadCarros()).thenReturn((byte) 10);
        //Act
        try {
            servicioParqueadero.guardarCarro(carro);
        } catch (PlacaNoPermitidaExcepcion excepcion) {
            //Assert
            assertEquals(excepcionPlacaNoPermitida, excepcion.getMessage());
        }
    }

    @Test
    public void guardarMoto_sinCupoParaIngresar_exitoso() {
        //Arrange
        Moto moto = new Moto("AQW-5R4", 650);
        when(motoRepositorio.obtenerCantidadMotos()).thenReturn((byte) moto.CANTIDAD_MAXIMA_EN_PARQUEADERO);
        //Act
        try {
            servicioParqueadero.guardarMoto(moto);
        } catch (SinCupoExcepcion excepcion) {
            //Assert
            assertEquals(iniciarExcepcionSinCupo(), excepcion.getMessage());
        }
    }

    @Test
    public void guardarMoto_placaNoAutorizaDiaLunes_exitoso() throws ParseException {
        //Arrange
        Moto moto = new Moto("AQW-5R4", 300);
        moto.modificarFechaIngreso(fechaEntrada());
        String excepcionPlacaNoPermitida = "No está autorizado a ingresar";
        when(motoRepositorio.obtenerCantidadMotos()).thenReturn((byte) 5);
        //Act
        try {
            servicioParqueadero.guardarMoto(moto);
        } catch (PlacaNoPermitidaExcepcion excepcion) {
            //Assert
            assertEquals(excepcionPlacaNoPermitida, excepcion.getMessage());
        }
    }

}
