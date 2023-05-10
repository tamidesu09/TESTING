package com.example.testing.Database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testing.Model.Note;

import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict = REPLACE)
    void insert(Note notes);
    
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAll();

    @Query("UPDATE notes SET title =:title, notes=notes WHERE ID= :id")
    void update(int id, String title, String note);

    @Delete
    void delete(Note notes);



    @Query("UPDATE notes SET pinned=:pin WHERE ID= :id")
    void pin(int id, boolean pin);


}
