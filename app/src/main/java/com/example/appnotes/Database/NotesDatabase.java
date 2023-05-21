package com.example.appnotes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appnotes.DAO.NoteDao;
import com.example.appnotes.Entity.NoteEntity;

@Database(entities = {NoteEntity.class},version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static final String DATABASE="notes3_db";
    private static NotesDatabase intences;

    public static synchronized NotesDatabase getInstance(Context context) {
        if (intences==null) {
            intences= Room.databaseBuilder(context,
                            NotesDatabase.class,DATABASE)
                    .allowMainThreadQueries().build();
        }
        return intences;
    }

    public abstract NoteDao noteDao();

}
