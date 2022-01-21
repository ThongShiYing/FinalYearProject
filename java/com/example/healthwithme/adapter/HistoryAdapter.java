package com.example.healthwithme.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthwithme.R;
import com.example.healthwithme.helper.RecycleItemTouchHelper;
import com.example.healthwithme.model.DrinkWater;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


// Provide a reference to the views for eacg data item
class ViewHolder extends RecyclerView.ViewHolder {
    public TextView volume, date, time;

    ViewHolder(View view) {
        super(view);
        volume = view.findViewById(R.id.volume);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
    }
}

public class HistoryAdapter extends RecyclerView.Adapter<ViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    List<DrinkWater> waterList;
    String yesterday = "";

    //Connect firebase
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userID = fAuth.getCurrentUser().getUid();


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("aaa hh:mm");

    public HistoryAdapter(List<DrinkWater> waterList) {
        this.waterList = waterList;
    }

    // Create new Views
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_items, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (!yesterday.equals(waterList.get(i).getDate())) {
            yesterday = waterList.get(i).getDate();
            viewHolder.date.setVisibility(View.VISIBLE);
        }

        viewHolder.date.setText(waterList.get(i).getDate());
        viewHolder.volume.setText(String.valueOf(waterList.get(i).getWaterVolume() + "ml"));

        try {
            Date time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(waterList.get(i).getTime());
            viewHolder.time.setText(timeFormat.format(Objects.requireNonNull(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Return size of dataset
    @Override
    public int getItemCount() {
        return waterList.size();
    }

    @Override
    public void onItemDelete(int positon) {


        fStore.collection("water").document(userID).collection("water")
                .document(waterList.get(positon).getUid())
                .delete()
                .addOnSuccessListener(aVoid -> notifyDataSetChanged())
                .addOnFailureListener(e -> Log.w("Delete", "Error deleting document", e));

    }


}
