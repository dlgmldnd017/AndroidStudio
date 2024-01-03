package com.exercise.samplerecyclerview2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.samplerecyclerview.R;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements OnPersonItemClickListener{
    ArrayList<Person> items = new ArrayList<>();
    OnPersonItemClickListener listener;

    // 먼저 아래의 코드가 실행되고
    // ViewHolder() 생성자에 View와 초기화한 listener를 파라메터로 전달한다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.person_item, parent, false);
        Log.d("TAG1","onCreateViewHolder 실행");
        return new ViewHolder(itemView, this);
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

        public ViewHolder(@NonNull View itemView, final OnPersonItemClickListener listener) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);

            // 만약 view 중에 클릭 될 경우, View의 setOnclickListner()가 호출되며
            // listener를 안이 아닌 밖에서 처리하기 위해 구현한 객체의 onItemClick()을 호출한다.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        Log.d("TAG1","itemView의 onItemClick 실행");
                        listener.onItemClick(ViewHolder.this, itemView, position);
                        // listener에 this가 포함되어 있기 때문에, 자신의 인스턴스 메서드 onItemClick을 호출함.
                        // 의문점1) ViewHolder.this는 어떤 것을 참조하는 것인가?
                        // 의문점2) 28줄에 생성자 호출할때 this 대신 인스턴스 변수 listener를 쓸 수 없는 것인가?
                    }
                }
            });
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

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            Log.d("TAG1", "PersonAdapter의 onItemClick 실행");
            listener.onItemClick(holder, view, position);
            // listener를 구현한 객체의 메서드를 호출함.
        }
    }

    public void setOnItemClickListener(OnPersonItemClickListener listener){
        Log.d("TAG1", "setOnItemClickListener 실행");
        this.listener = listener;
    }
}