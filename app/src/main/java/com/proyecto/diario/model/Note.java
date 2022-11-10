package com.proyecto.diario.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "location")
    public String location;
}
