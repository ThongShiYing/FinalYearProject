package com.example.healthwithme.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthwithme.R;
import com.example.healthwithme.adapter.HistoryAdapter;
import com.example.healthwithme.helper.RecycleItemTouchHelper;
import com.example.healthwithme.model.DrinkWater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WaterHistoryPageActivity extends AppCompatActivity {
    private static final String TAG = "";
    private final List<String> waterList = new ArrayList<>();
    private final List<DrinkWater> historyList = new ArrayList<>();

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    String time = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_water);
        recyclerView = findViewById(R.id.re_view);

        //      connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        HistoryAdapter mAdapter = new HistoryAdapter(historyList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback=new RecycleItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        fStore.collection("water").document(userID).collection("water").addSnapshotListener((value, error) -> {

            if (value != null) {
                historyList.clear();

                for (DocumentSnapshot snapshot : value) {

                    DrinkWater drinkWater = new DrinkWater();
                    drinkWater.setUid(snapshot.getId());
                    drinkWater.setWaterVolume(Math.toIntExact(Objects.requireNonNull(snapshot.getLong("waterVolume"))));
                    drinkWater.setDate(snapshot.getString("date"));
                    drinkWater.setTime(snapshot.getString("time"));

                    historyList.add(drinkWater);
                }
                Collections.sort(historyList);


                mAdapter.notifyDataSetChanged();

            }


        });


    }
}