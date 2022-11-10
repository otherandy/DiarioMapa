package com.proyecto.diario.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.proyecto.diario.model.Note;

@Database(entities = {Note.class}, views = {}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
