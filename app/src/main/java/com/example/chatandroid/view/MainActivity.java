package com.example.chatandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatandroid.R;
import com.example.chatandroid.controller.MainController;

public class MainActivity extends AppCompatActivity {

    private EditText chatRoomET;
    private EditText usernameET;
    private EditText passwordET;
    private TextView signUpTV;
    private Button signInBtn;
    private Button continueAsBtn;


    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatRoomET = findViewById(R.id.chatRoomET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        signUpTV = findViewById(R.id.signUpTV);
        signInBtn = findViewById(R.id.signInBtn);
        continueAsBtn = findViewById(R.id.continueAsBtn);

        controller = new MainController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }

    public EditText getChatRoomET() {
        return chatRoomET;
    }

    public EditText getUsernameET() {
        return usernameET;
    }

    public EditText getPasswordET() {
        return passwordET;
    }

    public TextView getSignUpTV() {
        return signUpTV;
    }

    public Button getSignInBtn() {
        return signInBtn;
    }

    public Button getContinueAsBtn() {
        return continueAsBtn;
    }
}
