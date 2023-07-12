package com.rit.NewsApplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rit.NewsApplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news,parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        Articles articles = articlesArrayList.get(position);
        holder.titleTV.setText(articles.getTitle());
        holder.subtitleTV.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            Intent i1;
            @Override
            public void onClick(View view) {

                i1 = new Intent(context, NewsdetailActivity.class);

                i1.putExtra("title", articles.getTitle());
                i1.putExtra("content", articles.getContent());
                i1.putExtra("desc", articles.getDescription());
                i1.putExtra("image", articles.getUrlToImage());
                i1.putExtra("url", articles.getUrl());

                context.startActivity(i1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView titleTV, subtitleTV;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.TVNewsHeading);
            subtitleTV = itemView.findViewById(R.id.TVSubtitle);
            newsIV = itemView.findViewById(R.id.IVNews);
        }
    }
}
