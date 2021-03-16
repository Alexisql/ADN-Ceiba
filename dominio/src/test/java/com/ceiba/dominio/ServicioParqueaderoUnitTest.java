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

    private String excepcionSinCupoMensaje;

    @Before
    public void inicializarVariables() {
        carroRepositorio = Mockito.mock(CarroRepositorio.class);
        motoRepositorio = Mockito.mock(MotoRepositorio.class);
        servicioParqueadero = new ServicioParqueadero(carroRepositorio, motoRepositorio);
    }

    private void iniciarExcepcion(){
        excepcionSinCupoMensaje = "No hay cupo disponible";
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
        iniciarExcepcion();
        when(carroRepositorio.obtenerCantidadCarros()).thenReturn((byte) carro.CANTIDAD_MAXIMA_EN_PARQUEADERO);
        //Act
        try {
            servicioParqueadero.guardarCarro(carro);
            fail();
        } catch (SinCupoExcepcion excepcion) {
            //Assert
            assertEquals(excepcionSinCupoMensaje, excepcion.getMessage());
        }
    }

    @Test
    public void guardarMoto_sinCupoParaIngresar_exitoso() {
        //Arrange
        Moto moto = new Moto("AQW-5R4", 650);
        iniciarExcepcion();
        when(motoRepositorio.obtenerCantidadMotos()).thenReturn((byte) moto.CANTIDAD_MAXIMA_EN_PARQUEADERO);
        //Act
        try {
            servicioParqueadero.guardarMoto(moto);
            fail();
        } catch (SinCupoExcepcion excepcion) {
            //Assert
            assertEquals(excepcionSinCupoMensaje, excepcion.getMessage());
        }
    }

}
