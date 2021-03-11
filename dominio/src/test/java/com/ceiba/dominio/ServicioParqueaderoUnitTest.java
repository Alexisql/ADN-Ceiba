package com.ceiba.dominio;

import com.ceiba.dominio.entidad.Carro;
import com.ceiba.dominio.entidad.Moto;
import com.ceiba.dominio.excepcion.SinCupoExcepcion;
import com.ceiba.dominio.repositorio.CarroRepositorio;
import com.ceiba.dominio.repositorio.MotoRepositorio;
import com.ceiba.dominio.servicio.ServicioParqueadero;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServicioParqueaderoUnitTest {

    @Mock
    CarroRepositorio carroRepositorio;

    @Mock
    MotoRepositorio motoRepositorio;

    ServicioParqueadero servicioParqueadero;

    Carro carro;
    Moto moto;
    String excepcionMensaje;

    @Before
    public void inicializarVariables() {
        carroRepositorio = Mockito.mock(CarroRepositorio.class);
        motoRepositorio = Mockito.mock(MotoRepositorio.class);
        //parkingService = Mockito.mock(ParkingService.class);
        servicioParqueadero = new ServicioParqueadero(carroRepositorio, motoRepositorio);
        carro = new Carro("AQW-5R4", "car");
        moto = new Moto("AQW-5R4", "motorcycle", (short) 650);
        excepcionMensaje = "No hay cupo disponible";
    }


    @Test
    public void validarPlaca_placaIniciadaEnAyElDiaViernes_exitoso() {
        //Arrange
        String placa = "ASD-J87";
        int diaViernes = Calendar.FRIDAY;
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
        int diaLunes = Calendar.MONDAY;
        boolean resultadoEsperado;
        //Act
        resultadoEsperado = servicioParqueadero.validarPlaca(placa, diaLunes);
        //Asset
        assertEquals(true, resultadoEsperado);

    }

    @Test
    public void guardarCarro_sinCupoParaIngresar_exitoso() {
        //Arrange
        when(carroRepositorio.obtenerCantidadCarros()).thenReturn((byte) 20);
        //Act
        try {
            servicioParqueadero.guardarCarro(carro);
            fail();
        } catch (SinCupoExcepcion exception) {
            //Assert
            assertEquals(excepcionMensaje, exception.getMessage());
        }
    }

    @Test
    public void guardarMoto_sinCupoParaIngresar_exitoso() {
        //Arrange
        when(motoRepositorio.obtenerCantidadMotos()).thenReturn((byte) 10);
        //Act
        try {
            servicioParqueadero.guardarMoto(moto);
            fail();
        } catch (SinCupoExcepcion exception) {
            //Assert
            assertEquals(excepcionMensaje, exception.getMessage());
        }
    }

}
