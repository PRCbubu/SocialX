package com.example.socialx.dao;

import androidx.room.Dao;

import androidx.room.Query;

import com.example.socialx.entities.NewsEntities;

import java.util.List;

@Dao
public interface NewsDao
{
    @Query("SELECT * FROM NewsEntities ORDER BY id DESC")
    List<NewsEntities> getAllNews();

}
