package com.example.socialx.HolderAndAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialx.R;

public class recyclerViewHolder extends RecyclerView.ViewHolder
{
    protected TextView PublishedAt;
    protected TextView SourceAt;
    protected TextView Title;
    protected TextView Description;
    protected ImageView NewsImage;

    public recyclerViewHolder(@NonNull View itemView)
    {
        super(itemView);

        PublishedAt = itemView.findViewById(R.id.PublishedAt);
        SourceAt = itemView.findViewById(R.id.SourceAt);
        Title = itemView.findViewById(R.id.Title);
        Description = itemView.findViewById(R.id.Description);
        NewsImage = itemView.findViewById(R.id.NewsImage);
    }
}
