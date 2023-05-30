package com.example.socialx.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UserEntities", indices = {@Index(value = "Email", unique = true), @Index(value = "Number", unique = true)})
public class UserEntities implements Serializable
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

    public UserEntities(String Email, String Password, String Name, String Number)
    {
        this.Email = Email;
        this.Password = Password;
        this.Name = Name;
        this.Number = Number;
    }

//    @Ignore
//   public UserEntities() {}


    public int getId()
    {
        return id;
    }

    public String getEmail()
    {
        return Email;
    }

    public String getPassword()
    {
        return Password;
    }

    public String getName()
    {
        return Name;
    }

    public String getNumber()
    {
        return Number;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public void setPassword(String password)
    {
        Password = password;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public void setNumber(String number)
    {
        Number = number;
    }

}
