package com.google.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.privatesharingapp.bean.DeliveryBean;

public class MyOrderActivity extends AppCompatActivity {
    // Declare the necessary views and button
    private TextView from, time, to, time2, weight, type, width, height, length;
    private Button getEstimate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        // Get the DeliveryBean object from the previous activity
        DeliveryBean deliveryBean = (DeliveryBean) getIntent().getSerializableExtra("delivery");

        // Initialize and set the values of various TextViews with data from DeliveryBean
        from = findViewById(R.id.from);
        from.setText("From Sender：" + deliveryBean.getPickLocation());
        time = findViewById(R.id.time);
        time.setText("Pick up time：" + deliveryBean.getDateTime());
        to = findViewById(R.id.to);
        to.setText("To Receiver：" + deliveryBean.getDropLocation());
        time2 = findViewById(R.id.time2);
        time2.setText("Pick up time：" + deliveryBean.getDateTime());
        weight = findViewById(R.id.weight);
        weight.setText(deliveryBean.getWeight());
        type = findViewById(R.id.type);
        type.setText(deliveryBean.getType());
        width = findViewById(R.id.width);
        width.setText(deliveryBean.getWidth());
        height = findViewById(R.id.height);
        height.setText(deliveryBean.getHeight());
        length = findViewById(R.id.length);
        length.setText(deliveryBean.getLength());

        // Initialize the Get Estimate button and set a listener
        getEstimate = findViewById(R.id.getEstimate);
        getEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to the EstimateActivity
                Intent intent = new Intent(MyOrderActivity.this, EstimateActivity.class);
                // Create a bundle to pass the DeliveryBean object to the EstimateActivity
                Bundle bundle = new Bundle();
                bundle.putSerializable("delivery", deliveryBean);
                intent.putExtras(bundle);
                // Start the EstimateActivity
                startActivity(intent);
            }
        });
    }
}