package com.ceiba.domain.repository;

import com.ceiba.domain.entity.Car;

import java.util.List;

public interface CarRepository {

    List<Car> getCars();

    void saveCar(Car car);

    void deleteCar(Car car);

    byte getCountCars();

}
