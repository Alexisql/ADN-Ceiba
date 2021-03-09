package com.ceiba.domain;

import com.ceiba.domain.entity.Car;
import com.ceiba.domain.entity.Motorcycle;
import com.ceiba.domain.exception.SinCupoException;
import com.ceiba.domain.repository.CarRepository;
import com.ceiba.domain.repository.MotorcycleRepository;
import com.ceiba.domain.service.ParkingService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    CarRepository carRepository = Mockito.mock(CarRepository.class);

    @Mock
    MotorcycleRepository motorcycleRepository = Mockito.mock(MotorcycleRepository.class);

    @Mock
    ParkingService parkingService = new ParkingService(carRepository, motorcycleRepository);


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
        assertTrue(true);
    }

    @Test
    public void saveCarNoQuota() {
        //Arrange
        Car car = new Car("AQW-5D5", "car");
        when(carRepository.getCountCars()).thenReturn((byte) 20);
        String exceptionMsj = "No hay cupo disponible";
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
        Motorcycle motorcycle = new Motorcycle("AQW-5D5", "motorcycle", (short) 9650);
        when(carRepository.getCountCars()).thenReturn((byte) 10);
        String exceptionMsj = "No hay cupo disponible";
        //Act
        try {
            parkingService.saveMotorcycle(motorcycle);
        } catch (SinCupoException exception) {
            //Assert
            assertEquals(exceptionMsj, exception.getMessage());
        }
    }
}
