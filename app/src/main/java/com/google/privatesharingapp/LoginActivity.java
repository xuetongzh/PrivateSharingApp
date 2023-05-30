package com.google.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.privatesharingapp.bean.TruckBean;
import com.google.privatesharingapp.bean.UserBean;
import com.google.privatesharingapp.mmkv.KVConfigImpl;
import com.luck.picture.lib.utils.ToastUtils;
import com.permissionx.guolindev.PermissionX;

import org.litepal.LitePal;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register, login;
    private EditText username, password;
    private String user, pwd;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize buttons and set click listeners
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        // Initialize EditText fields
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        if (LitePal.findAll(TruckBean.class).size() == 0) {
            initData();
        }
        initPermissions();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                // Get the entered username and password
                user = username.getText().toString();
                pwd = password.getText().toString();

                // Check if the username or password is empty
                if (user.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the entered username exists in the database
                if (LitePal.where("userName = ?", user).find(UserBean.class).size() == 0) {
                    Toast.makeText(LoginActivity.this, "Account does not exist, please sign up", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the UserBean object associated with the entered username
                userBean = LitePal.where("userName = ?", user).find(UserBean.class).get(0);
                // Check if the entered password matches the stored password
                if (userBean.getPassword().equals(pwd)) {
                    // Store the username by using KVConfigImpl
                    KVConfigImpl.getKVConfigImpl().setString("userName", userBean.getUserName());
                    // Start the MainActivity and finish the LoginActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showToast(LoginActivity.this, "Incorrect username or password");
                }
                break;
            case R.id.register:
                // Start the RegisterActivity and finish the LoginActivity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
        }
    }

    private void initData() {  // Initialize sample TruckBean data
        new TruckBean(R.drawable.truck_green, "22/2", "the weight(kg) is 100,the length of the goods is(cm):100,the width of the goods is(cm):100,the length of the goods is(cm):100").save();
        new TruckBean(R.drawable.truck_pink, "23/4", "the weight(kg) is 100,the length of the goods is(cm):100,the width of the goods is(cm):100,the length of the goods is(cm):100").save();
        new TruckBean(R.drawable.truck_blue, "22/9", "the weight(kg) is 100,the length of the goods is(cm):100,the width of the goods is(cm):100,the length of the goods is(cm):100").save();
        new TruckBean(R.drawable.truck_red, "22/4", "the weight(kg) is 100,the length of the goods is(cm):100,the width of the goods is(cm):100,the length of the goods is(cm):100").save();
        new TruckBean(R.drawable.truck_green, "22/8", "the weight(kg) is 100,the length of the goods is(cm):100,the width of the goods is(cm):100,the length of the goods is(cm):100").save();
    }

    private void initPermissions() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        Toast.makeText(this, "The following permissions are prohibited to run" + grantedList + "Please allow and restart the app", Toast.LENGTH_LONG).show();
                    }
                });
    }
}