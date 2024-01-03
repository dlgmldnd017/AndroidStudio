package com.example.samplefragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ListFragment.ImageSelection{
    ListFragment listFragment;
    ImgFragment imgFragment;

    int[] resId = {R.drawable.dream01, R.drawable.dream02, R.drawable.dream03};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        listFragment = (ListFragment) manager.findFragmentById(R.id.list_frag);
        imgFragment = (ImgFragment) manager.findFragmentById(R.id.img_frag);
    }

    public void setImage(int i){
        imgFragment.setImage(resId[i]);
    }
}