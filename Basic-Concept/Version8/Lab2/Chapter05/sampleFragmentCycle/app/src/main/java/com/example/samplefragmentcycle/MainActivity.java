package com.example.samplefragmentcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    MenuFragment menuFragment;

    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();
    }

    public void onChangeFragment(){
        if(i==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).commit();
            i=1;
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
            i=0;
        }
    }
}