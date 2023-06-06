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

    @Query("Select * from UserEntities where isLoggedIn = :checklogin limit 1")
    boolean checkLoggedIn(Boolean checklogin);

    @Query("select * from UserEntities where Email = :email and Password = :password limit 1")
    UserEntities findUserByEmailAndPassword(String email, String password);

    @Insert()
    void insertDetails(UserEntities userEntities);



}
