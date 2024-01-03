package com.example.samplefragment2;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ListFragment extends Fragment {
    MainActivity mainActivity;

    interface ImageSelection{
       void setImage(int i);
    }

    ImageSelection callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ImageSelection){
            callback = (ImageSelection) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        mainActivity = (MainActivity) getActivity();

        Button btn1 = v.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.setImage(0);
            }
        });

        Button btn2 = v.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.setImage(1);
            }
        });

        Button btn3 = v.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.setImage(2);
            }
        });
        return v;
    }
}