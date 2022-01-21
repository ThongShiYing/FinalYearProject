package com.example.healthwithme.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleItemTouchHelper extends ItemTouchHelper.Callback {


    private final ItemTouchHelperCallback helperCallback;

    public RecycleItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int swipFlags = ItemTouchHelper.END;
        return makeMovementFlags(0,swipFlags);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }



    public interface ItemTouchHelperCallback {
        void onItemDelete(int positon);
    }
}


