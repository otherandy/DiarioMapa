package com.proyecto.diario.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.proyecto.diario.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes order by id ASC")
    List<Note> getAll();

    @Query("UPDATE notes SET title = :title, note = :note WHERE id = :id")
    void update(int id, String title, String note);
}
