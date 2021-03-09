package com.ceiba.domain.service;

import com.ceiba.domain.Calculator;
import com.ceiba.domain.Constants;
import com.ceiba.domain.entity.Car;
import com.ceiba.domain.entity.Motorcycle;
import com.ceiba.domain.entity.Vehicle;
import com.ceiba.domain.exception.PlacaNoPermitidaException;
import com.ceiba.domain.exception.SinCupoException;
import com.ceiba.domain.repository.CarRepository;
import com.ceiba.domain.repository.MotorcycleRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class ParkingService {

    private CarRepository carRepository;
    private MotorcycleRepository motorcycleRepository;

    @Inject
    public ParkingService(CarRepository carRepository, MotorcycleRepository motorcycleRepository) {
        this.carRepository = carRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.addAll(carRepository.getCars());
        vehicleList.addAll(motorcycleRepository.getMotorcycles());
        return vehicleList;
    }


    public void saveCars(Car car) {
        byte countVehicle = carRepository.getCountCars();
        int dayCurrent = Calendar.getInstance().getFirstDayOfWeek();
        if (countVehicle == 20) {
            throw new SinCupoException();
        } else if (validatePlate(car.getPlaca(), dayCurrent)) {
            throw new PlacaNoPermitidaException();
        } else {
            carRepository.saveCar(car);
        }
    }

    public void saveMotorcycle(Motorcycle motorcycle) {
        byte countVehicle = motorcycleRepository.getCountMotorcycle();
        int dayCurrent = Calendar.getInstance().getFirstDayOfWeek();
        if (countVehicle == 10) {
            throw new SinCupoException();
        } else if (validatePlate(motorcycle.getPlaca(), dayCurrent)) {
            throw new PlacaNoPermitidaException();
        } else {
            motorcycleRepository.saveMotorcycle(motorcycle);
        }
    }

    public void deleteCar(Car car) {
        carRepository.deleteCar(car);
    }

    public void deleteMotorcycle(Motorcycle motorcycle) {
        motorcycleRepository.deleteMotorcycle(motorcycle);
    }

    public boolean validatePlate(String placa, int dayCurrent) {
        return (placa.startsWith("A") && (dayCurrent < 3));
    }

    public int valueTotalParkingMotorcycle(Motorcycle motorcycle) {
        Calendar dateEntry = motorcycle.getFechaIngreso();
        Calendar dateExit = Calendar.getInstance();
        int valuetTotal = Calculator.valueSubTotalParkingVehicle(dateEntry, dateExit, motorcycle.getTipo());
        if (motorcycle.getCilindraje() > Constants.CYLINDER_MOTORCYCLE) {
            valuetTotal += Constants.EXEDENT_MOTORCYCLE;
        }
        return valuetTotal;
    }

    public int valueTotalParkingCar(Car car) {
        Calendar dateEntry = car.getFechaIngreso();
        Calendar dateExit = Calendar.getInstance();
        return Calculator.valueSubTotalParkingVehicle(dateEntry, dateExit, car.getTipo());
    }

}
