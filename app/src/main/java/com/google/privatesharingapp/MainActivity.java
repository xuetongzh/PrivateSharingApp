package com.google.privatesharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.privatesharingapp.adapter.TruckAdapter;
import com.google.privatesharingapp.bean.TruckBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView main_recyclerView;
    private List<TruckBean> truckBeans = new ArrayList<>();
    private TruckAdapter truckAdapter;
    private FloatingActionButton addDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve all TruckBean objects from the LitePal database
        truckBeans = LitePal.findAll(TruckBean.class);
        // Set up the Add Delivery button
        addDelivery = findViewById(R.id.addDelivery);
        addDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the NewDeliveryActivity when the button is clicked
                startActivity(new Intent(MainActivity.this, NewDeliveryActivity.class));
            }
        });

        // Set up the RecyclerView to display the list of truckBeans
        main_recyclerView = findViewById(R.id.main_recyclerView);
        main_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        truckAdapter = new TruckAdapter(this, truckBeans);
        main_recyclerView.setAdapter(truckAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu layout to display the options menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_order:
                startActivity(new Intent(MainActivity.this, DeliveryActivity.class));
                break;
            default:
        }
        return true;
    }
}