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
import com.example.healthwithme.model.FoodCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class CaloriesViewHolder extends RecyclerView.ViewHolder {
    public TextView foodname,portion,calories, date, time;

    CaloriesViewHolder(View view) {
        super(view);
        foodname = view.findViewById(R.id.foodname);
        portion = view.findViewById(R.id.portion);
        calories = view.findViewById(R.id.calories);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
    }
}

public class CaloriesHistoryAdapter extends RecyclerView.Adapter<CaloriesViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    List<FoodCalories> caloriesList;
    String yesterday = "";

    //Connect firebase
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userID = fAuth.getCurrentUser().getUid();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("aaa hh:mm");

    public CaloriesHistoryAdapter(List<FoodCalories> caloriesList) {
        this.caloriesList = caloriesList;
    }

    // Create new Views
    @NonNull
    @Override
    public CaloriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.calories_list_item, viewGroup, false);

        return new CaloriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CaloriesViewHolder viewHolder, int i) {
        if (!yesterday.equals(caloriesList.get(i).getDate())) {
            yesterday = caloriesList.get(i).getDate();
            viewHolder.date.setVisibility(View.VISIBLE);
        }
        viewHolder.date.setText(caloriesList.get(i).getDate());
        viewHolder.foodname.setText(caloriesList.get(i).getFoodname());
        viewHolder.portion.setText(caloriesList.get(i).getPortion());
        viewHolder.calories.setText(String.valueOf(caloriesList.get(i).getFoodCalories() + "Cal"));

        try {
            Date time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(caloriesList.get(i).getTime());
            viewHolder.time.setText(timeFormat.format(Objects.requireNonNull(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    // Return size of dataset
    @Override
    public int getItemCount() {
        return caloriesList.size();
    }

    @Override
    public void onItemDelete(int positon) {


        fStore.collection("calories").document(userID).collection("calories")
                .document(caloriesList.get(positon).getUid())
                .delete()
                .addOnSuccessListener(aVoid -> notifyDataSetChanged())
                .addOnFailureListener(e -> Log.w("Delete", "Error deleting document", e));

    }
}
