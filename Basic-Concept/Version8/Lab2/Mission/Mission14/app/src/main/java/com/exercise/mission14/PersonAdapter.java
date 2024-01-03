package com.exercise.mission14;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>
                                    implements OnPersonItemClickListener{
    ArrayList<Person> items = new ArrayList<>();
    OnPersonItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.person_list, parent, false);
        return new ViewHolder(view, this);
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

    public void addItem(Person item){
        items.add(item);
    }

    public void subItem(Person item){
        items.remove(item);
    }

    public Person getItem(int position){
        return items.get(position);
    }

    @Override
    public void onItemClick(int position) {
        if(listener != null){
            listener.onItemClick(position);
        }
    }

    public void setOnClickItem(OnPersonItemClickListener listener){
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv1, tv2, tv3; //각각 이름, 가격, 상품설명

        public ViewHolder(@NonNull View itemView, final OnPersonItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null){
                        listener.onItemClick(getAdapterPosition());
                    }
                    return false;
                }
            });
        }

        public void setItem(Person item){
            imageView.setImageResource(item.getImageId());
            tv1.setText(item.getName());
            tv2.setText(item.getPrice());
            tv3.setText(item.getInfo());
        }
    }
}
