package com.example.test002.Customer;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test002.OnItemLongClick.OnItemLongClickListener;
import com.example.test002.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>
        implements OnItemLongClickListener{
    ArrayList<Customer> items = new ArrayList<>();
    OnItemLongClickListener listener;
    int width;

    public CustomerAdapter(int width){
        this.width = width;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = items.get(position);
        holder.setText(customer.getName(), customer.getbirth(), customer.getNumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Customer getItemCustomer(int position){
        Customer customer = items.get(position);
        return customer;
    }

    public void add(Customer customer){
        items.add(customer);
    }

    public void sub(int position){
        items.remove(position);
    }

    public boolean isDup(int position){
        if(position == 0){
            return false;
        }
        Customer customer = items.get(position);
        String name = customer.getName();
        String birth = customer.getbirth();
        String number = customer.getNumber();

        for(int i=0; i<position; i++){
            Customer compCustomer = items.get(i);
            String compName = compCustomer.getName();
            String compBirth = compCustomer.getbirth();
            String compNumber = compCustomer.getNumber();

            if(name.equals(compName) && birth.equals(compBirth) && number.equals(compNumber)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onItemLongClick(int position) {
        if(listener != null){
            listener.onItemLongClick(position);
        }
    }

    public void setListener(OnItemLongClickListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2, tv3;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, OnItemLongClickListener listener) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.card_tv1);
            tv2 = itemView.findViewById(R.id.card_tv2);
            tv3 = itemView.findViewById(R.id.card_tv3);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.getLayoutParams().width = (width-80);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemLongClick(position);
                    }
                    return true;
                }
            });
        }

        public void setText(String name, String birth, String number){
            tv1.setText(name);
            tv2.setText(birth);
            tv3.setText(number);
        }
    }
}
