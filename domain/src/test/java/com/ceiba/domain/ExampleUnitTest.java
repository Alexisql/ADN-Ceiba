package com.ceiba.domain;

import com.ceiba.domain.entity.Car;
import com.ceiba.domain.entity.Motorcycle;
import com.ceiba.domain.exception.SinCupoException;
import com.ceiba.domain.repository.CarRepository;
import com.ceiba.domain.repository.MotorcycleRepository;
import com.ceiba.domain.service.ParkingService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Mock
    CarRepository carRepository;

    @Mock
    MotorcycleRepository motorcycleRepository;

    @Mock
    ParkingService parkingService;

    Car car;
    Motorcycle motorcycle;
    String exceptionMsj;
    Calendar dateEntry;
    Calendar dateExit;

    @Before
    public void init() {
        carRepository = Mockito.mock(CarRepository.class);
        motorcycleRepository = Mockito.mock(MotorcycleRepository.class);
        //parkingService = Mockito.mock(ParkingService.class);
        parkingService = new ParkingService(carRepository, motorcycleRepository);
        car = new Car("AQW-5R4", "car");
        motorcycle = new Motorcycle("AQW-5R4", "motorcycle", (short) 650);
        exceptionMsj = "No hay cupo disponible";
    }

    @Before
    public void initDates() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        //Fecha de entrada
        dateEntry = Calendar.getInstance();
        String dateInStringEntry = "09-03-2021 6:20:56";
        Date dateTmpEntry = sdf.parse(dateInStringEntry);
        dateEntry.setTime(dateTmpEntry);
        //Fecha salida
        dateExit = Calendar.getInstance();
        String dateInStringExit = "09-03-2021 10:30:56";
        Date dateTmpExit = sdf.parse(dateInStringExit);
        dateExit.setTime(dateTmpExit);
    }


    @Test
    public void validatePlacaSuccessful() {
        //Arrange
        String plate = "ASD-J87";
        int dayFriday = Calendar.FRIDAY;
        boolean expectedResult;
        //Act
        expectedResult = parkingService.validatePlate(plate, dayFriday);
        //Asset
        assertFalse(expectedResult);
    }

    @Test
    public void validatePlacaFail() {
        //Arrange
        String plate = "ASD-J87";
        int dayMonday = Calendar.MONDAY;
        boolean expectedResult;
        //Act
        expectedResult = parkingService.validatePlate(plate, dayMonday);
        //Asset
        assertTrue(expectedResult);
    }

    @Test
    public void saveCarNoQuota() {
        //Arrange
        when(carRepository.getCountCars()).thenReturn((byte) 20);
        //Act
        try {
            parkingService.saveCars(car);
        } catch (SinCupoException exception) {
            //Assert
            assertEquals(exceptionMsj, exception.getMessage());
        }
    }

    @Test
    public void saveMotorcycleNoQuota() {
        //Arrange
        when(motorcycleRepository.getCountMotorcycle()).thenReturn((byte) 10);
        //Act
        try {
            parkingService.saveMotorcycle(motorcycle);
        } catch (SinCupoException exception) {
            //Assert
            assertEquals(exceptionMsj, exception.getMessage());
        }
    }

    @Test
    public void calculateHoursInparking() {
        //Act
        int subTotal = Calculator.calculateHoursInparking(dateEntry, dateExit);
        //Assert
        assertEquals(5, subTotal);

    }

    @Test
    public void valueSubTotalParkingVehicleSuccessful() {
        //Act
        int subTotal = Calculator.valueSubTotalParkingVehicle(dateEntry, dateExit, car.getTipo());
        //Assert
        assertEquals(5000, subTotal);
    }

   /* @Test
    public void calculateTotalParkingMotorcycle() {
        //Arrange
        when(motorcycle.getFechaIngreso()).thenReturn(dateEntry);
        //Act
        int total = parkingService.valueTotalParkingMotorcycle(motorcycle);
        //Assert
    }*/

}
