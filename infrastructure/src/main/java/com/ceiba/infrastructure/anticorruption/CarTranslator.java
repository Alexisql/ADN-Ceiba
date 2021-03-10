package com.ceiba.infrastructure.anticorruption;

import com.ceiba.domain.entity.Car;
import com.ceiba.infrastructure.db.entity.CarEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CarTranslator {

    public static Car fromCarDBToCarDomain(CarEntity carEntity) throws ParseException {
        Car car = new Car(carEntity.plate, carEntity.type);
        Calendar fechaIngreso = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaIngreso.setTime(sdf.parse(String.valueOf(fechaIngreso)));
        car.setFechaIngreso(fechaIngreso);
        return car;
    }

    public static CarEntity fromCarDomainToCarBD(Car car) {
        CarEntity carEntity = new CarEntity();
        carEntity.setPlate(car.getPlaca());
        carEntity.setType(car.getTipo());
        carEntity.setDateEntry(car.getFechaIngreso().toString());
        return carEntity;
    }

    public static List<Car> listCarsFromEntityToEntityDB(List<CarEntity> carList) throws ParseException {
        List<Car> carListTmp = new ArrayList<>();
        for (CarEntity carEntity : carList) {
            carListTmp.add(fromCarDBToCarDomain(carEntity));
        }
        return carListTmp;
    }
}
