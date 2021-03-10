package com.ceiba.infrastructure.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "motorcycle_entity")
public class MotorcycleEntity {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "plate")
    public String plate;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "dateEntry")
    public Date dateEntry;

    @ColumnInfo(name = "cylinder")
    public int cylinder;
}
