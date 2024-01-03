package com.example.samplefragment2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImgFragment extends Fragment {
    ImageView img1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_img, container, false);

        img1 = v.findViewById(R.id.img1);

        return v;
    }

    public void setImage(int imgRes){
        img1.setImageResource(imgRes);
    }
}