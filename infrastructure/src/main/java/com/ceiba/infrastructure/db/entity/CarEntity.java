package com.ceiba.infrastructure.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "car_entity")
public class CarEntity{

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "plate")
    public String plate;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "dateEntry")
    public String dateEntry;

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateEntry(String dateEntry) {
        this.dateEntry = dateEntry;
    }
}
