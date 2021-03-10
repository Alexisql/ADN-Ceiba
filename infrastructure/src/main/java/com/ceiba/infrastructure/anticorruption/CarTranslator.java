package com.ceiba.infrastructure.anticorruption;

import com.ceiba.domain.entity.Car;
import com.ceiba.infrastructure.db.entity.CarEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CarTranslator {

    public static Car fromCarDBToCarDomain(CarEntity carEntity) {
        Car car = new Car(carEntity.plate, carEntity.type);
        Calendar fechaIngreso = Calendar.getInstance();
        fechaIngreso.setTime(carEntity.dateEntry);
        car.setFechaIngreso(fechaIngreso);
        return car;
    }

    public static CarEntity fromCarDomainToCarBD(Car car) {
        CarEntity carEntity = new CarEntity();
        carEntity.setPlate(car.getPlaca());
        carEntity.setType(car.getTipo());
        carEntity.setDateEntry(car.getFechaIngreso().getTime());
        return carEntity;
    }

    public static List<Car> listCarsFromEntityToEntityDB(List<CarEntity> carList) {
        List<Car> carListTmp = new ArrayList<>();
        for (CarEntity carEntity : carList) {
            carListTmp.add(fromCarDBToCarDomain(carEntity));
        }
        return carListTmp;
    }
}
