package com.example.samplemovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    ArrayList<Movie> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = items.get(position);
        holder.setItem(movie);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie movie){
        items.add(movie);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.card_tv1);
            tv2 = itemView.findViewById(R.id.card_tv2);
        }

        public void setItem(Movie movie) {
            tv1.setText(movie.movieNm);
            tv2.setText(movie.openDt);
        }
    }
}
