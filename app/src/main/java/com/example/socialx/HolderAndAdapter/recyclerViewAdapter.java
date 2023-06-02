package com.example.socialx.HolderAndAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialx.R;
import com.example.socialx.model.NewsAPI_Article;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewHolder>
{
    private final Context context;
    private final List<NewsAPI_Article> headlines;

    public recyclerViewAdapter(Context context, List<NewsAPI_Article> headlines)
    {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new recyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder holder, int position)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDate date = LocalDate.parse(headlines.get(position).getPublishedAt(), formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        if(headlines.get(position).getNewsImage()!= null)
        {
            Picasso.get().load(headlines.get(position).getNewsImage()).into(holder.NewsImage);
        }

        holder.PublishedAt.setText(date.format(outputFormatter));
        holder.SourceAt.setText(headlines.get(position).getSources().getName());
        holder.Title.setText(headlines.get(position).getTitle());
        holder.Description.setText(headlines.get(position).getDescription());


    }

    @Override
    public int getItemCount()
    {
        return headlines.size();
    }
}
