package com.ceiba.domain;

import com.ceiba.domain.entity.Car;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class CalculatorTest {

    Calendar dateEntry;
    Calendar dateExit;
    Car car;

    @Before
    public void init() throws ParseException {
        car = new Car("AQW-5R4", "car");
        initDates();
    }

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
}