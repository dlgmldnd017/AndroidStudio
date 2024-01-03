package com.example.test003.Customer;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test003.R;
import com.example.test003.ItemTouchHelper.ItemTouchHelperListener;
import com.example.test003.OnItemLongClick.OnItemLongClickListener;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>
        implements OnItemLongClickListener, ItemTouchHelperListener {

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

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        Customer person = items.get(from_position);

        //이동할 객체 삭제
        items.remove(from_position);

        //이동하고 싶은 position에 추가
        items.add(to_position,person);

        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);

        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2, tv3;
        CheckBox box;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, OnItemLongClickListener listener) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.card_tv1);
            tv2 = itemView.findViewById(R.id.card_tv2);
            tv3 = itemView.findViewById(R.id.card_tv3);

            cardView = itemView.findViewById(R.id.cardView);
            cardView.getLayoutParams().width = width-80;

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.CENTER | Gravity.RIGHT;

            box = itemView.findViewById(R.id.checkBox);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d("llll", items.size()+" asddsa");
                    if(box.getVisibility() == View.VISIBLE){
                        param.setMargins(0,0,0,0);
                        box.setLayoutParams(param);
                        box.setVisibility(View.INVISIBLE);

                    }else if(box.getVisibility() == View.INVISIBLE){
                        param.setMargins(50,30,30,30);
                        box.setLayoutParams(param);
                        box.setVisibility(View.VISIBLE);

                    }

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