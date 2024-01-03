package com.exercise.samplerecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{
    ArrayList<Person> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.person_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }

        public void setItem(Person item){
            tv1.setText(item.getName());
            tv2.setText(item.getMobile());
        }
    }

    public void addItem(Person item){
        items.add(item);
    }

    public void setItems(ArrayList<Person> items){
        this.items = items;
    }

    public Person getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Person item){
        items.set(position, item);
    }
}
