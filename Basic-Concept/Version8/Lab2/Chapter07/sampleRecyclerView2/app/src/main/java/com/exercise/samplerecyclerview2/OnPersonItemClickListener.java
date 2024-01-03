package com.exercise.samplerecyclerview2;

import android.view.View;

public interface OnPersonItemClickListener {
    void onItemClick(PersonAdapter.ViewHolder holder, View view, int position);
}
