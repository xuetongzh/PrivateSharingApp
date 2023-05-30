package com.google.privatesharingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode;
import com.github.gzuliyujiang.wheelpicker.annotation.TimeMode;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;
import com.google.privatesharingapp.bean.DeliveryBean;
import com.google.privatesharingapp.bean.UserBean;
import com.google.privatesharingapp.mmkv.KVConfigImpl;
import com.luck.picture.lib.utils.ToastUtils;

import org.litepal.LitePal;

public class NewDeliveryActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_REGISTER = 1;
    public static final int REQUEST_CODE_REGISTER2 = 3;
    private UserBean userBean;
    private LinearLayout view1, view2;
    private EditText receiverName;
    private TextView pickUpLo, dropOffLo;
    private TextView pickUpDate, pickUpTime, time1, time2, from, to;
    private Button next, callDriver;
    private DatimePicker picker;

    private String name, date, time, pickLo, dropLo;
    private EditText weight, type, width, height, length;
    private String weightS, typeS, widthS, heightS, lengthS, pickLocationLat, pickLocationLon, dropLocationLat, dropLocationLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        // Initialize UI elements and objects
        userBean = LitePal.where("userName = ?", KVConfigImpl.getKVConfigImpl().getString("userName", "")).find(UserBean.class).get(0);
        picker = new DatimePicker(this);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);

        receiverName = findViewById(R.id.receiverName);
        pickUpDate = findViewById(R.id.pickUpDate);
        pickUpDate.setOnClickListener(this);
        pickUpTime = findViewById(R.id.pickUpTime);
        pickUpTime.setOnClickListener(this);
        pickUpLo = findViewById(R.id.pickUpLo);
        pickUpLo.setOnClickListener(new View.OnClickListener() {  // Set click listener for button
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewDeliveryActivity.this, PickLocationActivity.class), REQUEST_CODE_REGISTER);
            }
        });
        dropOffLo = findViewById(R.id.dropOffLo);
        dropOffLo.setOnClickListener(new View.OnClickListener() {  // Set click listener for button
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewDeliveryActivity.this, PickLocationActivity.class), REQUEST_CODE_REGISTER2);
            }
        });
        next = findViewById(R.id.next);
        next.setOnClickListener(this);

        time1 = findViewById(R.id.time);
        time2 = findViewById(R.id.time2);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        weight = findViewById(R.id.weight);
        type = findViewById(R.id.type);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        length = findViewById(R.id.length);
        callDriver = findViewById(R.id.callDriver);
        callDriver.setOnClickListener(this);
    }

    // Handle button click events
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickUpDate:
                pickUpDate();
                picker.setOnDatimePickedListener((year, month, day, hour, minute, second) -> {
                    pickUpDate.setText(year + "-" + month + "-" + day);
                });
                break;
            case R.id.pickUpTime:
                pickUpDate();
                picker.setOnDatimePickedListener((year, month, day, hour, minute, second) -> {
                    pickUpTime.setText(hour + "-" + minute);
                });
                break;
            case R.id.next:
                // Retrieve user input data
                name = receiverName.getText().toString();
                date = pickUpDate.getText().toString();
                time = pickUpTime.getText().toString();
                pickLo = pickUpLo.getText().toString();
                dropLo = dropOffLo.getText().toString();

                // Check if any field is empty
                if (name.isEmpty() || date.isEmpty() || time.isEmpty() || pickLo.isEmpty() || dropLo.isEmpty()) {
                    ToastUtils.showToast(NewDeliveryActivity.this, "Please enter full information");
                    return;
                }

                // Set values for display
                from.setText(userBean.getNickName());
                to.setText(userBean.getNickName());
                time1.setText(date + " " + time);
                time2.setText(date + " " + time);

                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.callDriver:
                // Retrieve user input data
                weightS = weight.getText().toString();
                typeS = type.getText().toString();
                widthS = width.getText().toString();
                heightS = height.getText().toString();
                lengthS = length.getText().toString();

                // Check if any field is empty
                if (weightS.isEmpty() || typeS.isEmpty() || widthS.isEmpty() || heightS.isEmpty() || lengthS.isEmpty()) {
                    ToastUtils.showToast(NewDeliveryActivity.this, "Please enter full information");
                    return;
                }

                if (new DeliveryBean(userBean.getUserName(), name, date + " " + time, pickLo, pickLocationLat, pickLocationLon, dropLo, dropLocationLat, dropLocationLon, weightS, typeS, widthS, heightS, lengthS).save()) {
                    ToastUtils.showToast(NewDeliveryActivity.this, "Call successful");
                    finish();
                }
                break;
        }
    }

    private void pickUpDate() {
        final DatimeWheelLayout wheelLayout = picker.getWheelLayout();
        //Set the mode of date and time
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
        wheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND);
        wheelLayout.setRange(DatimeEntity.yearOnFuture(0), DatimeEntity.yearOnFuture(10));
        wheelLayout.setDateLabel("Year", "Month", "Day");
        wheelLayout.setTimeLabel("Hour", "Minute", "Second");
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == PickLocationActivity.RESULT_CODE_REGISTER && data != null) {
            Bundle extras = data.getExtras();

            //Get the address, latitude and longitude from the extra data of the intent
            String address = extras.getString("address", "");
            String lat = extras.getString("lat", "");
            String lon = extras.getString("lon", "");

            pickUpLo.setText(address);
            this.pickLocationLat = lat;
            this.pickLocationLon = lon;
        } else if (requestCode == REQUEST_CODE_REGISTER2 && resultCode == PickLocationActivity.RESULT_CODE_REGISTER && data != null) {
            Bundle extras = data.getExtras();

            String address = extras.getString("address", "");
            String lat = extras.getString("lat", "");
            String lon = extras.getString("lon", "");

            dropOffLo.setText(address);
            this.dropLocationLat = lat;
            this.dropLocationLon = lon;
        }
    }
}