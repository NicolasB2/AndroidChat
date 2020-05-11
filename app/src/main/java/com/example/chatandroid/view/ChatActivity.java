package com.example.chatandroid.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatandroid.R;
import com.example.chatandroid.controller.ChatController;

public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private TextView usernameTV;
    private ListView messagesList;
    private ImageView messageIV;
    private Button galBtn,sendBtn,signOutBtn;
    private ConstraintLayout controlsContainer;

    private ChatController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageET = findViewById(R.id.messageET);
        usernameTV = findViewById(R.id.usernameTV);
        messagesList = findViewById(R.id.messagesList);
        messageIV = findViewById(R.id.messageIV);
        galBtn = findViewById(R.id.galBtn);
        sendBtn = findViewById(R.id.sendBtn);
        signOutBtn = findViewById(R.id.signOutBtn);

        controlsContainer = findViewById(R.id.controlsContainer);

        controller = new ChatController(this);
    }

    public EditText getMessageET() {
        return messageET;
    }

    public TextView getUsernameTV() {
        return usernameTV;
    }

    public ListView getMessagesList() {
        return messagesList;
    }

    public ImageView getMessageIV() {
        return messageIV;
    }

    public Button getGalBtn() {
        return galBtn;
    }

    public Button getSendBtn() {
        return sendBtn;
    }

    public Button getSignOutBtn() {
        return signOutBtn;
    }

    public ConstraintLayout getControlsContainer() {
        return controlsContainer;
    }

    //la actividad pierde el primer plano
    @Override
    protected void onPause() {
        controller.beforePause();
        super.onPause();
    }

    //la actividad recupera el primer plano
    @Override
    protected void onResume() {
        controller.beforeResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        controller.onActivityResult(requestCode,resultCode,data);
    }

    public void hideImage(){
        messageIV.setVisibility(View.GONE);
    }

    public void ahowImage(){
        messageIV.setVisibility(View.VISIBLE);
    }
}
