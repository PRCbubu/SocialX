package com.example.socialx.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.socialx.dao.UserDao;
import com.example.socialx.entities.UserEntities;

@Database(entities = {UserEntities.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase
{
    public static final String DB_NAME = "UserDatabase_DB";
    private static UserDatabase userDetailsDatabase;

    public static synchronized UserDatabase getDatabase(Context context)
    {
        if(userDetailsDatabase == null)
        {
            userDetailsDatabase = Room.databaseBuilder(
                    context,
                    UserDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return userDetailsDatabase;
    }

    public abstract UserDao userDao();
}
