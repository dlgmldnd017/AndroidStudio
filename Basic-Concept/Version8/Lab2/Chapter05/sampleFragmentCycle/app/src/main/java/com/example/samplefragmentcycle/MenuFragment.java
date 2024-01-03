package com.example.samplefragmentcycle;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("llll", "onCreateView");
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        Button btn = v.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onChangeFragment();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("llll", "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("llll", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("llll", "onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("llll", "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("llll", "onPause");
    }
}