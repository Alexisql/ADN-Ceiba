package com.ceiba.infrastructure.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ceiba.infrastructure.db.dao.CarDao;
import com.ceiba.infrastructure.db.dao.MotorcycleDao;
import com.ceiba.infrastructure.db.entity.CarEntity;
import com.ceiba.infrastructure.db.entity.MotorcycleEntity;

@Database(entities = {CarEntity.class, MotorcycleEntity.class}, version = 1)
public abstract class DataBaseManager extends RoomDatabase {

    private static DataBaseManager instance = null;

    public abstract CarDao carDao();

    public abstract MotorcycleDao motorcycleDao();

    public static DataBaseManager getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    DataBaseManager.class, "adn-ceiba").build();
        }
        return instance;
    }
}
