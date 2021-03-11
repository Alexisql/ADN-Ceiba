package com.ceiba.infrastructure.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ceiba.infrastructure.db.entity.CarEntity;

import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insertCar(CarEntity carEntity);

    @Query("SELECT * FROM car_entity")
    List<CarEntity> getAllCars();

    @Delete
    void deleteCar(CarEntity carEntity);

    @Query("SELECT COUNT(*) FROM car_entity")
    byte getCountsCars();

}
