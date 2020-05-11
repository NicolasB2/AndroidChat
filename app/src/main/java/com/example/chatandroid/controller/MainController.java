package com.example.chatandroid.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.chatandroid.R;
import com.example.chatandroid.model.User;
import com.example.chatandroid.view.ChatActivity;
import com.example.chatandroid.view.MainActivity;
import com.example.chatandroid.view.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainController implements View.OnClickListener{

    private MainActivity activity;

    public MainController(MainActivity activity){
        this.activity = activity;

        activity.getSignInBtn().setOnClickListener(this);
        activity.getSignUpTV().setOnClickListener(this);
        activity.getContinueAsBtn().setOnClickListener(this);

        ActivityCompat.requestPermissions(activity,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        },0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInBtn:

                String chatRoom = activity.getChatRoomET().getText().toString();
                String email = activity.getUsernameET().getText().toString();
                String password = activity.getPasswordET().getText().toString();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(
                            authResult -> {
                                Intent intent = new Intent(activity,ChatActivity.class);
                                intent.putExtra("chatRoom",chatRoom);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                    ).addOnFailureListener(e->{
                    Toast.makeText(activity,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });

                break;

            case R.id.signUpTV:
                Intent i = new Intent(activity, SignUpActivity.class);
                activity.startActivity(i);
                break;

            case R.id.continueAsBtn:
                String chatRoomToContinue = activity.getChatRoomET().getText().toString();

                if (chatRoomToContinue.trim().isEmpty()){
                    Toast.makeText(activity,"Debe ingresar el chatroom para continuar",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent continueIntent = new Intent(activity,ChatActivity.class);
                continueIntent.putExtra("chatRoom",chatRoomToContinue);
                activity.startActivity(continueIntent);
                activity.finish();

                break;
        }
    }

    public void onResume() {
         if(FirebaseAuth.getInstance().getUid() == null){
             activity.getContinueAsBtn().setVisibility(View.GONE);
         }else{
             activity.getContinueAsBtn().setVisibility(View.VISIBLE);
             FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
             .addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     User user = dataSnapshot.getValue(User.class);
                     activity.getContinueAsBtn().setText("Continuar como "+user.getUsername());
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
         }
    }
}
