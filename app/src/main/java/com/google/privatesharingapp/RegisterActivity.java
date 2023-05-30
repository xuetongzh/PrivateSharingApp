package com.google.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.privatesharingapp.bean.UserBean;
import com.google.privatesharingapp.widget.GlideEngine;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.utils.ToastUtils;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView selectImage;
    private Button register;
    private final PictureSelectorStyle pictureSelectorStyle = new PictureSelectorStyle();
    private String user, pwd, cpwd, nickName, head, phone;
    private EditText username, nickname, password, cpassword, phoneNum;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the EditText fields
        username = findViewById(R.id.username);
        nickname = findViewById(R.id.nickname);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        phoneNum = findViewById(R.id.phone);

        // Set up the click listener for the selectImage ImageView
        selectImage = findViewById(R.id.selectImage);
        selectImage.setOnClickListener(this);
        // Set up the click listener for the register button
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        initPermissions();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImage:
                // Open the gallery to select an image by using PictureSelector library
                PictureSelector.create(this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setMaxSelectNum(1)
                        .setSelectorUIStyle(pictureSelectorStyle)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                // Retrieve the selected image path and display it by using Glide library
                                head = result.get(0).getRealPath();
                                Glide.with(getApplicationContext()).load(new File(head)).into(selectImage);
                            }

                            @Override
                            public void onCancel() {
                                ToastUtils.showToast(RegisterActivity.this, "Cancel selection");
                            }
                        });
                break;
            case R.id.register:
                // Get the input values from the EditText fields
                user = username.getText().toString();
                pwd = password.getText().toString();
                cpwd = cpassword.getText().toString();
                nickName = nickname.getText().toString();
                phone = phoneNum.getText().toString();

                // Check if any field is empty
                if (user.isEmpty() || pwd.isEmpty() || nickName.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all information", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the profile is selected
                if (head == null || head.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please select a profile", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the entered passwords match
                if (pwd.equals(cpwd)) {
                    userBean = new UserBean(user, pwd, head, nickName, phone);
                    if (userBean.save()) {
                        Toast.makeText(RegisterActivity.this, "Sign up successfully, turn to login page soon", Toast.LENGTH_SHORT).show();
                        // Redirect to the login page after a short delay
                        toLogin();
                    } else {
                        ToastUtils.showToast(RegisterActivity.this, "Sign up failed");
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "The entered passwords do not match", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void toLogin() {  // Method to redirect to the login page after a delay
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    private void initPermissions() {  // Method to initialize required permissions by using PermissionX library
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        ToastUtils.showToast(getApplicationContext(), "The following permissions are prohibited " + deniedList);
                    }
                });
    }
}