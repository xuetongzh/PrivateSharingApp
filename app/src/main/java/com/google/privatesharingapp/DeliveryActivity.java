package com.google.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.privatesharingapp.adapter.DeliveryAdapter;
import com.google.privatesharingapp.bean.DeliveryBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private List<DeliveryBean> deliveryBeans = new ArrayList<>();  // List to store DeliveryBean objects
    private RecyclerView recyclerView;  // RecyclerView to display the list of deliveries
    private DeliveryAdapter deliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);  // Set the layout for this activity


        recyclerView = findViewById(R.id.deliveryRec);
        deliveryBeans = LitePal.findAll(DeliveryBean.class);  // Retrieve all DeliveryBean objects from the LitePal database
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        deliveryAdapter = new DeliveryAdapter(this, deliveryBeans);
        recyclerView.setAdapter(deliveryAdapter);
    }
}