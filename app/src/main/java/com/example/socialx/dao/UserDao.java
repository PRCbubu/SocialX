package com.example.socialx.dao;

import androidx.room.Dao;

import androidx.room.Insert;

import androidx.room.Query;


import com.example.socialx.entities.UserEntities;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("select * from UserEntities")
    List<UserEntities> getAllUsers();

    @Query("select * from UserEntities where Email = :email or Number = :number limit 1")
    UserEntities findUserByEmailOrNumber(String email, String number);

    @Query("select * from UserEntities where Email = :email or Password = :password limit 1")
    UserEntities findUserByEmailOrPassword(String email, String password);


    @Insert()
    void insertDetails(UserEntities userEntities);

}
