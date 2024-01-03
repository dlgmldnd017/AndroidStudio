package com.example.test003.ItemTouchHelper;

public interface ItemTouchHelperListener {
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
}
