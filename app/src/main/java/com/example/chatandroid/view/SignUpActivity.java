package com.example.chatandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatandroid.R;
import com.example.chatandroid.controller.SignUpController;

public class SignUpActivity extends AppCompatActivity {

    private SignUpController controller;

    private EditText nameET;
    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText rePasswordET;
    private TextView iHaveUserTV;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        rePasswordET = findViewById(R.id.rePasswordET);
        iHaveUserTV = findViewById(R.id.iHaveUserTV);
        signInBtn = findViewById(R.id.signInBtn);

        controller = new SignUpController(this);
    }

    public SignUpController getController() {
        return controller;
    }

    public EditText getNameET() {
        return nameET;
    }

    public EditText getEmailET() {
        return emailET;
    }

    public EditText getUsernameET() {
        return usernameET;
    }

    public EditText getPasswordET() {
        return passwordET;
    }

    public EditText getRePasswordET() {
        return rePasswordET;
    }

    public TextView getiHaveUserTV() {
        return iHaveUserTV;
    }

    public Button getSignInBtn() {
        return signInBtn;
    }
}
