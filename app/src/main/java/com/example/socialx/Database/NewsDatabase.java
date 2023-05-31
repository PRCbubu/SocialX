package com.example.socialx.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.socialx.dao.NewsDao;
import com.example.socialx.entities.NewsEntities;

@Database(entities = {NewsEntities.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase
{
    public static final String DB_NAME = "NewsDatabase_DB";
    private static NewsDatabase newsDatabase;

    public static synchronized NewsDatabase getDatabase(Context context)
    {
        if(newsDatabase == null)
        {
            newsDatabase = Room.databaseBuilder(
                    context,
                    NewsDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return newsDatabase;
    }

    public abstract NewsDao newsDao();
}
