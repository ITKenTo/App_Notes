package com.example.appnotes.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnotes.Entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(NoteEntity noteEntity);

    @Delete
    void Delete(NoteEntity noteEntity);

    @Update
    void update(NoteEntity noteEntity);

    @Query("SELECT * FROM notesapp ORDER BY id DESC")
    List<NoteEntity> getAllNotes();


}
