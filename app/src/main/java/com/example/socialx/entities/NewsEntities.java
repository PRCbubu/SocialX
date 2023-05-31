package com.example.socialx.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "NewsEntities")
public class NewsEntities implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "PublishedAt")
    private String PublishedAt;

    @ColumnInfo(name = "NewsSource")
    private String NewsSource;

    @ColumnInfo(name = "ImagePath")
    private String ImagePath;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getPublishedAt()
    {
        return PublishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        PublishedAt = publishedAt;
    }

    public String getNewsSource()
    {
        return NewsSource;
    }

    public void setNewsSource(String newsSource)
    {
        NewsSource = newsSource;
    }

    public String getImagePath()
    {
        return ImagePath;
    }

    public void setImagePath(String imagePath)
    {
        ImagePath = imagePath;
    }

    //    @NonNull
//    @Override
//    public String toString()
//    {
//        return title +" : "+date_time;
//    }
}
