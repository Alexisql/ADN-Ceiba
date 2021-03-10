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

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ParkingServiceUnitTest {

    @Mock
    CarRepository carRepository;

    @Mock
    MotorcycleRepository motorcycleRepository;

    ParkingService parkingService;

    Car car;
    Motorcycle motorcycle;
    String exceptionMsj;

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

   /* @Test
    public void calculateTotalParkingMotorcycle() {
        //Arrange
        when(motorcycle.getFechaIngreso()).thenReturn(dateEntry);
        //Act
        int total = parkingService.valueTotalParkingMotorcycle(motorcycle);
        //Assert
    }*/

}
