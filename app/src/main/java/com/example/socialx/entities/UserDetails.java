package com.example.socialx.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "UserDetails")
public class UserDetails implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Password")
    private String Password;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Number")
    private String Number;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        Password = password;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getNumber()
    {
        return Number;
    }

    public void setNumber(String number)
    {
        Number = number;
    }

    //    @NonNull
//    @Override
//    public String toString()
//    {
//        return title +" : "+date_time;
//    }
}
