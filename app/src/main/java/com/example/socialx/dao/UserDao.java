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

    @Query("Update UserEntities Set isLoggedIn = 0")
    void setToLogOut();

    @Query("select * from UserEntities where Email = :email and Password = :password limit 1")
    UserEntities findUserByEmailAndPassword(String email, String password);

    @Query("Update UserEntities set isLoggedIn =:isLogged where Email = :email and Password = :password")
    void updateLoggedInCheck(String email, String password, boolean isLogged);

    @Insert()
    void insertDetails(UserEntities userEntities);



}
