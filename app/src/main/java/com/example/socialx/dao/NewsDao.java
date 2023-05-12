package com.example.socialx.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.socialx.entities.News;

import java.util.List;

@Dao
public interface NewsDao
{
    @Query("SELECT * FROM News ORDER BY id DESC")
    List<News> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(News note);

    @Delete
    void deleteNote(News note);
}
