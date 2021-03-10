package com.ceiba.infrastructure.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ceiba.infrastructure.db.entity.MotorcycleEntity;

import java.util.List;

@Dao
public interface MotorcycleDao {

    @Insert
    void insertMotorcycle(MotorcycleEntity motorcycleEntity);

    @Query("SELECT * FROM motorcycle_entity")
    List<MotorcycleEntity> getAllMotorcycle();

    @Delete
    void deleteMotorcycle(MotorcycleEntity motorcycleEntity);

    @Query("SELECT COUNT(*) FROM motorcycle_entity")
    byte getCountsMotorcycle();
}
