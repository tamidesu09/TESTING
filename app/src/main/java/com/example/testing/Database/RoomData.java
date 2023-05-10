package com.example.testing.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testing.Model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class RoomData extends RoomDatabase {

    private static RoomData database;
    private static String DATABASE_NAME = "NotesApp";

    public synchronized static RoomData getInstance(Context context){
        if (database==null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomData.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract DAO dao();

}
