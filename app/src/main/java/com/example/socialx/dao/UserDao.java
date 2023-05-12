package com.example.socialx.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.socialx.entities.UserDetails;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM UserDetails ORDER BY id DESC")
    List<UserDetails> getAllUsers();

    @Query("SELECT * FROM UserDetails WHERE Email LIKE :Email AND password LIKE :password")
    List<UserDetails> getEmailAndPassword(String Email, String password);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(UserDetails note);

    @Delete
    void deleteNote(UserDetails note);
}
